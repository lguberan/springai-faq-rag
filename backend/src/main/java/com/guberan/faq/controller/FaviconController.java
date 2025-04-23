package com.guberan.faq.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FaviconController {

    @GetMapping("favicon.ico")
    public ResponseEntity<byte[]> getFavicon() throws IOException {
        ClassPathResource imgFile = new ClassPathResource("static/favicon.ico");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("image/x-icon"))
                .body(bytes);
    }
}