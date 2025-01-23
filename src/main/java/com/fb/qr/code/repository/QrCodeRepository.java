package com.fb.qr.code.repository;

import com.fb.qr.code.domain.QrCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends MongoRepository<QrCode, String> {

}
