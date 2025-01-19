package com.example.productservicebackend.service;

import com.example.productservicebackend.entities.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image UploadImage(MultipartFile file) throws IOException;
    Image getImageDetails(Long id) throws  IOException;
    ResponseEntity<byte[]> getImage(Long id) throws  IOException;
    void DeleteImage(Long Id);
}
