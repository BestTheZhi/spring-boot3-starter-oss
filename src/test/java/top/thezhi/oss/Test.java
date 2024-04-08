package top.thezhi.oss;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import top.thezhi.oss.annotation.AliyunOssFileStorageService;
import top.thezhi.oss.annotation.LocalFileStorageService;
import top.thezhi.oss.annotation.MinIOFileStorageService;
import top.thezhi.oss.config.LocalAutoConfig;
import top.thezhi.oss.config.MinIOAutoConfig;
import top.thezhi.oss.config.OssAliyunAutoConfig;
import top.thezhi.oss.service.FileStorageService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author ZHI LIU
 * @date 2023-09-03
 */

@SpringBootTest(classes = {MinIOAutoConfig.class, OssAliyunAutoConfig.class, LocalAutoConfig.class})
public class Test {

    @Autowired
//    @MinIOFileStorageService
//    @AliyunOssFileStorageService
    @LocalFileStorageService
    FileStorageService fileStorageService;

    @org.junit.jupiter.api.Test
    public void test1() throws FileNotFoundException {
        InputStream is = new FileInputStream("D:\\Code\\TheZhi\\Project\\Roco\\img\\破晓凯瑞尔.png");
        String url = fileStorageService.upload("test", "破晓凯瑞尔.png", is);
        System.out.println(url);
    }

    @org.junit.jupiter.api.Test
    public void test2() {
        InputStream inputStream = fileStorageService.download("test/破晓凯瑞尔.png");
        System.out.println(inputStream);
    }


    @org.junit.jupiter.api.Test
    public void test3() {
        boolean success = fileStorageService.delete("test/破晓凯瑞尔.png");
        System.out.println(success);
    }


}
