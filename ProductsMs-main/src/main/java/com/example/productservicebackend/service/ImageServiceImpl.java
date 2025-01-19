package com.example.productservicebackend.service;

import com.example.productservicebackend.entities.Image;
import com.example.productservicebackend.repos.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;


    @Override
    public Image UploadImage(MultipartFile file) throws IOException {
        return imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes()).build());
    }

    @Override
    public Image getImageDetails(Long id) throws IOException {
       Image image = imageRepository.findById(id).get();
        return image;
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {
        Image dbImage = imageRepository.findById(id).get();
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.getType()))
                .body(dbImage.getImage());
    }

    @Override
    public void DeleteImage(Long Id) {
        imageRepository.deleteById(Id);
        System.out.println("Deleted Successfully!");
    }
}
