package top.thezhi.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import top.thezhi.oss.config.OssAliyunConfigProperties;
import top.thezhi.oss.service.FileStorageService;
import java.io.InputStream;

/**
 * @author ZHI LIU
 *
 *
 */

@Slf4j
@top.thezhi.oss.annotation.AliyunOssFileStorageService
public class AliyunOssFileStorageService implements FileStorageService {

    @Autowired
    private OSS ossClient;

    @Autowired
    private OssAliyunConfigProperties ossAliyunConfigProperties;


    @Override
    public String upload(String prefix, String filename, InputStream inputStream) {
        //Object完整路径 例如exampleDir/exampleObject.txt。
        String filePath = prefix + "/" + filename;
        //文件url
        String url = null;
        //存储空间名称
        String bucketName = ossAliyunConfigProperties.getBucketName();
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            if (result != null) {
                log.info("OSS文件上传成功：{}" ,filePath);
                url = filePath;
            }
        } catch (OSSException | ClientException oe) {
            log.error("上传文件出错：{}",filePath);
        }
        return url;
    }

    @Override
    public InputStream download(String objectName) {
        InputStream content = null;
        try {
            // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
            OSSObject ossObject = ossClient.getObject(ossAliyunConfigProperties.getBucketName(), objectName);
            // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
            content = ossObject.getObjectContent();
        } catch (OSSException | ClientException oe) {
            log.error("文件下载失败：{}",objectName);
        }
        return content;
    }

    @Override
    public boolean delete(String objectName) {
        boolean success = true;
        try {
            // 删除文件。
            ossClient.deleteObject(ossAliyunConfigProperties.getBucketName(), objectName);
        } catch (OSSException | ClientException oe) {
            success = false;
        }
        return success;
    }


}
