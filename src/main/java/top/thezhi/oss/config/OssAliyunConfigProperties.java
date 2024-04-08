package top.thezhi.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ZHI LIU
 *
 *
 */


@Data
@ConfigurationProperties(prefix = "oss.aliyun")
public class OssAliyunConfigProperties {

    /***
     * 站点
     */
    private String webSite;

    /**
     * Bucket所在地域对应的Endpoint
     */
    private String endpoint ;

    /**
     * 秘钥Id
     */
    private String accessKeyId ;

    /**
     * 秘钥
     */
    private String accessKeySecret ;

    /**
     * 桶名称
     */
    private String bucketName ;

    /**
     * 设置OSSClient允许打开的最大HTTP连接数，默认为1024个
     */
    private Integer maxConnections = 1024;

    /**
     * 设置Socket层传输数据的超时时间，默认为50000毫秒
     */
    private Integer socketTimeout = 50000;

    /**
     * 设置建立连接的超时时间，默认为50000毫秒
     */
    private Integer connectionTimeout = 50000;

    /**
     * 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
     */
    private Integer connectionRequestTimeout = 60000;

    /**
     * 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
     */
    private Integer idleConnectionTime = 60000;

    /**
     * 设置失败请求重试次数，默认为3次。
     */
    private Integer maxErrorRetry = 3;

    /**
     * 设置用户代理，指HTTP的User-Agent头，默认为aliyun-sdk-java
     */
    private String userAgent = "aliyun-sdk-java";

}
