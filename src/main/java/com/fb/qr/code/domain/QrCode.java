package com.fb.qr.code.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class QrCode {

    @Id
    private String id;

    private String type;

    private String purpose;

    private String downloadUrl;

    private Map<String, Object> attributes;

    private boolean isActive;

    private boolean isDeleted;

    private Date created_at;

    private Date updated_at;

}
