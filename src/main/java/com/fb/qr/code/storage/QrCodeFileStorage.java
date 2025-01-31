package com.fb.qr.code.storage;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public interface QrCodeFileStorage {

    Map<String,Object> storeFile(byte[] fileContents, Map<String, String> properties);

    String generatePublicUrl(String filePath);

    ByteArrayOutputStream readFile(String filePath);

}
