package top.thezhi.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ZHI LIU
 *
 */

@Data
@ConfigurationProperties(prefix = "oss.local")
public class LocalConfigProperties {

    /**
     * 是否使用本地存储
     */
    private boolean enable;

    /**
     * 基本文件路径
     * D:/Code/temp/
     * /usr/local/img/
     */
    private String bucket;

    /***
     * 读取地址
     */
    private String readPath;

}
