package com.example.touristWebsite.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        String originalFileName = file.getOriginalFilename() == null
                ? "upload"
                : Paths.get(file.getOriginalFilename()).getFileName().toString();

        String safeFileName = originalFileName
                .replaceAll("[^a-zA-Z0-9._-]", "_");

        String fileName = System.currentTimeMillis() + "_" + safeFileName;
        Path path = uploadPath.resolve(fileName).normalize();
        Files.write(path, file.getBytes());

        return "/uploads/" + fileName;
    }
}
