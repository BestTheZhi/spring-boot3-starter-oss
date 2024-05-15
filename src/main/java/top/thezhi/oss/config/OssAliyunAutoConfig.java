package top.thezhi.oss.config;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import top.thezhi.oss.service.impl.AliyunOssFileStorageService;

/**
 * @author ZHI LIU
 *
 * oss自动配置类
 *
 */


@Slf4j
@EnableConfigurationProperties(OssAliyunConfigProperties.class)
@AutoConfiguration
@ConditionalOnClass(OSSClient.class)
@Import(AliyunOssFileStorageService.class)
public class OssAliyunAutoConfig {

    @Autowired
    OssAliyunConfigProperties ossAliyunConfigProperties;

    @Bean
    public ClientBuilderConfiguration clientBuilderConfiguration() {
        // 创建ClientConfiguration。
        // ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        conf.setMaxConnections(ossAliyunConfigProperties.getMaxConnections());
        // 设置Socket层传输数据的超时时间，默认为50000毫秒。
        conf.setSocketTimeout(ossAliyunConfigProperties.getSocketTimeout());
        // 设置建立连接的超时时间，默认为50000毫秒。
        conf.setConnectionTimeout(ossAliyunConfigProperties.getConnectionTimeout());
        // 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
        conf.setConnectionRequestTimeout(ossAliyunConfigProperties.getConnectionRequestTimeout());
        // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
        conf.setIdleConnectionTime(ossAliyunConfigProperties.getIdleConnectionTime());
        // 设置失败请求重试次数，默认为3次。
        conf.setMaxErrorRetry(ossAliyunConfigProperties.getMaxErrorRetry());
        // 设置是否开启二级域名的访问方式，默认不开启。
        conf.setSLDEnabled(false);
        // 设置连接OSS所使用的协议（HTTP/HTTPS），默认为HTTP。
        conf.setProtocol(Protocol.HTTP);
        // 设置用户代理，指HTTP的User-Agent头，默认为aliyun-sdk-java。
        conf.setUserAgent(ossAliyunConfigProperties.getUserAgent());
        return conf;
    }


    @Bean
    public OSS ossClient(ClientBuilderConfiguration conf){
        log.info("--------------------Aliyun - OSSClient创建开始--------------------");
        OSS ossClient = null;
        if(ossAliyunConfigProperties.getAccessKeyId() == null || ossAliyunConfigProperties.getAccessKeySecret()==null){
            // 使用环境变量中获取的RAM用户的访问密钥配置访问凭证。
            try {
                EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
                ossClient = new OSSClientBuilder().build(ossAliyunConfigProperties.getEndpoint(), credentialsProvider,conf);
            } catch (ClientException e) {
                throw new RuntimeException("未设置访问凭证(环境变量 和 代码嵌入 方式都未设置)");
            }
        }else{
            // 使用代码嵌入的RAM用户的访问密钥配置访问凭证。
            CredentialsProvider credentialsProvider = new DefaultCredentialProvider(ossAliyunConfigProperties.getAccessKeyId(), ossAliyunConfigProperties.getAccessKeySecret());
            ossClient =  new OSSClientBuilder().build(ossAliyunConfigProperties.getEndpoint(),credentialsProvider,conf);
        }
        //判断存储空间是否存在,不存在就创建
        if (!ossClient.doesBucketExist(ossAliyunConfigProperties.getBucketName())) {
            // 创建CreateBucketRequest对象。
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(ossAliyunConfigProperties.getBucketName());
            // 设置存储空间读写权限为公共读，默认为私有。
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            // 创建存储空间。
            ossClient.createBucket(createBucketRequest);
        }

        log.info("--------------------Aliyun - OSSClient创建成功--------------------");
        return ossClient;
    }
    








}
