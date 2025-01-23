package com.fb.qr.code.controller;

import com.fb.qr.code.domain.QrCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/app/v1")
public class QrCodeController {

    @PostMapping(value = "/qrcode",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createQrcode(@RequestBody QrCode qrcode){
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/qrcode/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getQrcode(@PathVariable("id") String id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/qrcode" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getQrCodes(@RequestParam(name="start", required = true) Integer start,
                                        @RequestParam(name="end", required = true) Integer end,
                                        @RequestParam(name="type") String type) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/qrcode/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteQrcode(@PathVariable("id") String id){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
