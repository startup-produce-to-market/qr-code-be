package com.fb.qr.code.generator;

import com.fb.qr.code.domain.QrCode;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface QrCodeGenerator {

         ResponseEntity<?> downLoadQr(String url);

         ResponseEntity<?> generateQr(Map<String,Object> request);

}
