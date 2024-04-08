package top.thezhi.oss.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.thezhi.oss.service.impl.LocalFileStorageService;

/**
 * @author ZHI LIU
 *
 */


@Configuration
@EnableConfigurationProperties(LocalConfigProperties.class)
@Import(LocalFileStorageService.class)
//当配置属性不存在时，默认是否匹配。默认为 false，表示配置属性不存在时不匹配。
@ConditionalOnProperty(prefix = "oss",name = "local.enable",havingValue = "true",matchIfMissing = false)
public class LocalAutoConfig {



}
