package com.example.productservicebackend.restcontrollers;

import com.example.productservicebackend.entities.Image;
import com.example.productservicebackend.entities.Produit;
import com.example.productservicebackend.service.ImageService;
import com.example.productservicebackend.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ProduitService produitService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image){
        try {
            Image savedImage =imageService.UploadImage(image);
            return new ResponseEntity<>(savedImage, HttpStatus.OK);

        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/info/{id}")
    public ResponseEntity<?> getImageInfos(@PathVariable("id") Long Id){
        try {
            Image image = imageService.getImageDetails(Id);
            if(image!=null){
                return new ResponseEntity<>(image,HttpStatus.OK);
            }
            return new ResponseEntity<>("Does not exists",HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id")Long Id){
        try {
            return imageService.getImage(Id);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/uploadFS/{id}")
    public void uploadImageFS(@RequestParam("image") MultipartFile file,@PathVariable("id") Long id) throws IOException {
        Produit p = produitService.getProduit(id); p.setImagePath(id+".jpg");
        Files.write(Paths.get(System.getProperty("user.home")+"/images/"+p.getImagePath())
                , file.getBytes());
        produitService.saveProduit(p);
    }


    @GetMapping("loadfromFS/{id}")
    public byte[] getImageFS(@PathVariable("id") Long id) throws IOException {
        Produit p = produitService.getProduit(id);
        return
                Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/images/"+p.getImagePath()));
    }




}
