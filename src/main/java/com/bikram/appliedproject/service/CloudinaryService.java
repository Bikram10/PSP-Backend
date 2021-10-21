package com.bikram.appliedproject.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String upload(MultipartFile multipartFile, String filename) throws IOException;

    String uploadImage(String url, String filename) throws IOException;

    void remove(String filepath) throws IOException;
}
