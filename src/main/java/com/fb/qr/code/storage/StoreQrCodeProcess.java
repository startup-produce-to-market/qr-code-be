package com.fb.qr.code.storage;

import com.fb.qr.code.error.ErrorConstants;
import com.fb.qr.code.error.GenerateQrCodeException;
import com.fb.qr.code.error.StoreQrFileException;
import com.fb.qr.code.repository.QrCodeRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class StoreQrCodeProcess implements Runnable {

    private final QrCodeFileStorage qrCodeFileStorage;

    private final byte[] fileContent;

    private final Map<String, String> fileProperties;

    private final QrCodeRepository qrCodeRepository;

    public StoreQrCodeProcess(QrCodeFileStorage qrCodeFileStorage,
                              byte[] fileContent,
                              Map<String, String> fileProperties,
                              QrCodeRepository qrCodeRepository) {
        this.qrCodeFileStorage = qrCodeFileStorage;
        this.fileContent = fileContent;
        this.fileProperties = fileProperties;
        this.qrCodeRepository = qrCodeRepository;
    }

    @Override
    public void run() {
        try {
            Map<String,Object> storeQrResponse =  qrCodeFileStorage.storeFile(fileContent, fileProperties);
            qrCodeRepository.findById(fileProperties.get("qrCodeId")).ifPresent(qrCode -> {
                    qrCode.setAttributes(Map.of("qrStorageAttributes", storeQrResponse));
                    qrCodeRepository.save(qrCode);
            });
        } catch (Exception e) {
            log.error("Error while storing the qr code {}", fileProperties.get("qrCodeId") , e);
        }
    }
}
