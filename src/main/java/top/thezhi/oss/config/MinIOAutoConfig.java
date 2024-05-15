package top.thezhi.oss.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import top.thezhi.oss.service.impl.MinIOFileStorageService;


@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({MinIOConfigProperties.class})
@ConditionalOnClass(MinioClient.class)
@Import(MinIOFileStorageService.class)
public class MinIOAutoConfig {

    @Autowired
    private MinIOConfigProperties minIOConfigProperties;

    @Bean
    public MinioClient buildMinioClient() {
        log.info("--------------------MinIO - OSSClient创建开始--------------------");
        MinioClient client = MinioClient
                .builder()
                .credentials(minIOConfigProperties.getAccessKey(),
                        minIOConfigProperties.getSecretKey())
                .endpoint(minIOConfigProperties.getEndpoint())
                .build();
        log.info("--------------------MinIO - OSSClient创建开始--------------------");
        return client;
    }
}
