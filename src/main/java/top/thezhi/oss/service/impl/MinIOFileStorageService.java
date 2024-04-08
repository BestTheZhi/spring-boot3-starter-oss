package top.thezhi.oss.service.impl;


import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import top.thezhi.oss.config.MinIOConfigProperties;
import top.thezhi.oss.service.FileStorageService;

import java.io.InputStream;

@Slf4j
@top.thezhi.oss.annotation.MinIOFileStorageService
public class MinIOFileStorageService implements FileStorageService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIOConfigProperties minIOConfigProperties;

    @Override
    public String upload(String prefix, String filename, InputStream inputStream) {
        //Object完整路径 例如exampleDir/exampleObject.txt。
        String filePath = prefix + "/" + filename;
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
//                    .contentType(contentType)
                    .bucket(minIOConfigProperties.getBucket()).stream(inputStream,inputStream.available(),-1)
                    .build();
            minioClient.putObject(putObjectArgs);
            log.info("OSS文件上传成功：{}" ,minIOConfigProperties.getBucket() + filePath);
            return filePath;
        }catch (Exception ex){
            log.error("minio put file error.",ex);
            throw new RuntimeException("上传文件失败");
        }
    }

    @Override
    public InputStream download(String objectName) {
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(minIOConfigProperties.getBucket()).object(objectName).build());
        } catch (Exception e) {
            log.error("minio down file error.  pathUrl:{}",minIOConfigProperties.getBucket() + objectName);
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    public boolean delete(String objectName) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(minIOConfigProperties.getBucket()).object(objectName).build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("minio remove file error.  pathUrl:{}",minIOConfigProperties.getBucket() + objectName);
            e.printStackTrace();
        }
        return true;
    }
}
