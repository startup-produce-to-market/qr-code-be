package com.fb.qr.code.storage;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

@ConditionalOnProperty(value = "qr.code.file.storage", havingValue = "dropbox")
@Component
public class DropBoxQrFileStorage implements QrCodeFileStorage {

    @Override
    public void storeFile(byte[] fileContents, Map<String, String> properties) {

    }

    @Override
    public String generatePublicUrl(String filePath) {
        return "";
    }

    @Override
    public ByteArrayOutputStream readFile(String filePath) {
        return new ByteArrayOutputStream();
    }
}
