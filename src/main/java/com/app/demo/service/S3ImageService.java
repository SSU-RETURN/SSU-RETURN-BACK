package com.app.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3ImageService {
    String upload(MultipartFile image);
    void deleteImageFromS3(String imageAddress);
}
