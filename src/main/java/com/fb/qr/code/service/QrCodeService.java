package com.fb.qr.code.service;

import com.fb.qr.code.domain.QrCode;
import com.fb.qr.code.error.ErrorConstants;
import com.fb.qr.code.error.GenerateQrCodeException;
import com.fb.qr.code.generator.QrCodeGenerator;
import com.fb.qr.code.generator.config.QrGeneratorConfig;
import com.fb.qr.code.repository.QrCodeRepository;
import com.fb.qr.code.storage.QrCodeFileStorage;
import com.fb.qr.code.storage.StoreQrCodeProcess;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Slf4j
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;

    private final QrCodeGenerator qrCodeGenerator;

    private final QrCodeFileStorage qrCodeFileStorage;

    private final QrGeneratorConfig qrGeneratorConfig;

    private ExecutorService executorService;

    public QrCodeService(QrCodeRepository qrCodeRepository,
                         QrCodeGenerator qrCodeGenerator,
                         QrCodeFileStorage qrCodeFileStorage,
                         QrGeneratorConfig qrGeneratorConfig) {
        this.qrCodeRepository = qrCodeRepository;
        this.qrCodeGenerator = qrCodeGenerator;
        this.qrCodeFileStorage = qrCodeFileStorage;
        this.qrGeneratorConfig = qrGeneratorConfig;

    }

    @PostConstruct
    private void createQrStorageProcessPool() {
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public QrCode createQrCode(QrCode qrCode) {
        try {
            buildQrObject(qrCode);
            generateAndStoreQrCode(qrCode);
            return qrCodeRepository.save(qrCode);
        } catch (Exception e) {
            throw GenerateQrCodeException.builder()
                    .errorCode(ErrorConstants.GENERATE_QR_CODE_ERROR_CODE)
                    .message(ErrorConstants.GENERATE_QR_CODE_ERROR_MSG)
                    .build();
        }
    }

    private void generateAndStoreQrCode(QrCode qrCode) {
        switch (this.qrGeneratorConfig.getQrCodeGenrator()) {
            case "QR_TIGER":
                Map<String, Object> qrGeneratorResponse = (Map<String, Object>) qrCodeGenerator.generateQr(Map.of()).getBody();
                assert qrGeneratorResponse != null;
                ResponseEntity<?> qrFileContent = qrCodeGenerator.downLoadQr(String.valueOf(
                        qrGeneratorResponse.get(qrGeneratorConfig.getQrCodeDownloadUrlReference())));
                executorService.execute(new StoreQrCodeProcess(qrCodeFileStorage, (byte[]) qrFileContent.getBody(),
                        Map.of(), qrCodeRepository));

            default:
                log.info("");

        }
    }

    private void buildQrObject(QrCode qrCode) {
        qrCode.setId(UUID.randomUUID().toString());
        qrCode.setActive(true);
        qrCode.setExpired(false);
        qrCode.setDownloadAvailable(false);
        qrCode.setCreated_at(new Date());
        qrCode.setUpdated_at(new Date());
    }
}
