package com.fb.qr.code.service;

import com.fb.qr.code.domain.QrCode;
import com.fb.qr.code.generator.QrCodeGenerator;
import com.fb.qr.code.repository.QrCodeRepository;
import com.fb.qr.code.storage.QrCodeFileStorage;
import org.springframework.stereotype.Service;

@Service
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;

    private final QrCodeGenerator qrCodeGenerator;

    private final QrCodeFileStorage qrCodeFileStorage;

    public QrCodeService(QrCodeRepository qrCodeRepository, QrCodeGenerator qrCodeGenerator, QrCodeFileStorage qrCodeFileStorage) {
        this.qrCodeRepository = qrCodeRepository;
        this.qrCodeGenerator = qrCodeGenerator;
        this.qrCodeFileStorage = qrCodeFileStorage;
    }

    public QrCode createQrCode(QrCode qrCode) {
        return qrCodeRepository.save(qrCode);
    }
}
