package com.fb.qr.code.error;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundException extends RuntimeException {

    private String message;

    private String errorCode;

    private Throwable exception;

    private Map<String, Object> errorAttributes;
}