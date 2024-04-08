package top.thezhi.oss.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "oss.minio")  // 文件上传 配置前缀file.minio
public class MinIOConfigProperties implements Serializable {

    /**
     * 秘钥Id
     */
    private String accessKey;
    /**
     * 秘钥
     */
    private String secretKey;
    /**
     * 桶名称
     */
    private String bucket;
    /***
     * 同读取地址
     */
    private String endpoint;
    /***
     * 读取地址
     */
    private String readPath;
}
