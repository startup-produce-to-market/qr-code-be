package com.fb.qr.code.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class QrCodeCommonConfig {

    @Value("${qr.code.generator}")
    private String qrCodeGenrator;
    @Value("${qr.code.file.storage}")
    private String qrCodeFileStorage;
    @Value("${qr.code.generator.url")
    private String qrGeneratorUrl;
    @Value("${qr.code.file.storage.extension}")
    private String qrCodeFileStorageExtension;
    @Value("${qr.code.file.storage.media}")
    private String qrCodeFileStorageMedia;

}
