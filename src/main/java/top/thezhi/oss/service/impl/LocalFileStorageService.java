package top.thezhi.oss.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import top.thezhi.oss.config.LocalConfigProperties;
import top.thezhi.oss.service.FileStorageService;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author ZHI LIU
 */

@Slf4j
@top.thezhi.oss.annotation.LocalFileStorageService
public class LocalFileStorageService implements FileStorageService {

    @Autowired
    private LocalConfigProperties localConfigProperties;

    @Override
    public String upload(String prefix, String filename, InputStream inputStream){
        //Object完整路径 例如exampleDir/exampleObject.txt。
        String filePath = prefix + "/" + filename;
        String path = localConfigProperties.getBucket() + filePath;
        try {
            Path targetPath = Paths.get(path);
            Files.createDirectories(targetPath.getParent()); // 创建父目录
            Files.copy(inputStream, targetPath);
            log.info("OSS文件上传成功：{}" ,filePath);
            return filePath;
        } catch (IOException e) {
            log.error("local put file error.",e);
            throw new RuntimeException("上传文件失败");
        }
    }

    @Override
    public InputStream download(String objectName) {
        InputStream inputStream = null;
        Path path = Paths.get(localConfigProperties.getBucket() + objectName);
        try {
            inputStream =  Files.newInputStream(path);
        } catch (IOException e) {
            log.error("local down file error.  pathUrl:{}",path);
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    public boolean delete(String objectName){
        Path file = Paths.get(localConfigProperties.getBucket() + objectName);
        try {
            Files.delete(file);
        } catch (IOException e) {
            log.error("local remove file error.  pathUrl:{}",file);
            e.printStackTrace();
        }
        return true;
    }

}
