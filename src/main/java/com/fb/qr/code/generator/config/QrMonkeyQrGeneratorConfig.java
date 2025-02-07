package com.fb.qr.code.generator.config;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QrMonkeyQrGeneratorConfig {

    private String qrData;

    private String qrDownload;

    private boolean qrCodeDownloadFile;

}
