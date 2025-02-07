package com.fb.qr.code.generator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class QrCodeGeneratorConfig {

    @Bean
    @ConfigurationProperties(prefix = "qrmonkey")
    @ConditionalOnProperty(name = "qr.code.generator.name", havingValue = "QR_MONKEY")
    public QrMonkeyQrGeneratorConfig qrMonkeyQrGeneratorConfig() {
        return new QrMonkeyQrGeneratorConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "qrtiger")
    @ConditionalOnProperty(name = "qr.code.generator.name", havingValue = "QR_TIGER")
    public QrTigerQrGeneratorConfig qrTigerQrGeneratorConfig() {
        return new QrTigerQrGeneratorConfig();
    }
}
