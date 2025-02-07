package com.fb.qr.code.generator;

import com.fb.qr.code.error.DownloadQrCodeException;
import com.fb.qr.code.error.ErrorConstants;
import com.fb.qr.code.error.GenerateQrCodeException;
import com.fb.qr.code.service.config.QrCodeCommonConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpQrCodeGenerator implements QrCodeGenerator {

    private final RestTemplate restTemplate;

    private final QrCodeCommonConfig qrCodeCommonConfig;

    public HttpQrCodeGenerator(RestTemplate restTemplate, QrCodeCommonConfig qrCodeCommonConfig) {
        this.restTemplate = restTemplate;
        this.qrCodeCommonConfig = qrCodeCommonConfig;
    }

    @Override
    public ResponseEntity<?> downLoadQr(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
        if (!response.getStatusCode().equals(HttpStatus.OK))
            throw DownloadQrCodeException.builder().message(ErrorConstants.GENERATE_QR_CODE_ERROR_MSG).errorCode(ErrorConstants.GENERATE_QR_CODE_ERROR_CODE).build();

        return response;
    }

    @Override
    public ResponseEntity<?> generateQr(Map<String, Object> request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(MediaType.parseMediaTypes("application/json; charset=utf-8"));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<HashMap> createQrResponse = restTemplate.exchange(qrCodeCommonConfig.getQrGeneratorUrl(), HttpMethod.POST, entity, HashMap.class);
        if (!createQrResponse.getStatusCode().equals(HttpStatus.CREATED))
            throw GenerateQrCodeException.builder().message(ErrorConstants.GENERATE_QR_CODE_ERROR_MSG).errorCode(ErrorConstants.GENERATE_QR_CODE_ERROR_CODE).build();

        return createQrResponse;
    }

    @Override
    public ResponseEntity<?> downloadQr(Map<String, Object> request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> createQrResponse = restTemplate.exchange(qrCodeCommonConfig.getQrGeneratorUrl(), HttpMethod.POST, entity, byte[].class);
        if (!createQrResponse.getStatusCode().equals(HttpStatus.CREATED))
            throw GenerateQrCodeException.builder().message(ErrorConstants.GENERATE_QR_CODE_ERROR_MSG).errorCode(ErrorConstants.GENERATE_QR_CODE_ERROR_CODE).build();

        return createQrResponse;
    }
}
