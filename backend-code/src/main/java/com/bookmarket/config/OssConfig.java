package com.bookmarket.config;

import com.aliyun.oss.OSS;
import com.bookmarket.utils.OssClientSingleton;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {

    @PreDestroy
    public void close() {
        OSS instance = OssClientSingleton.getInstance();
        if (instance != null) {
            instance.shutdown();
        }
    }
}
