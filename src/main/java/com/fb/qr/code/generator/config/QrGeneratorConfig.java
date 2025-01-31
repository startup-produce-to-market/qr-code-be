package com.fb.qr.code.generator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class QrGeneratorConfig {

    @Value("${qr.code.generator.url")
    private String qrGeneratorUrl;
    @Value("${qr.code.download.url.reference}")
    private String qrCodeDownloadUrlReference;
    @Value("${qr.code.generator}")
    private String qrCodeGenrator;
    @Value("${qr.code.file.storage}")
    private String qrCodeFileStorage;
}
