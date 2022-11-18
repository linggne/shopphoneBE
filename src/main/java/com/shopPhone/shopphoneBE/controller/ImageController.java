package com.shopPhone.shopphoneBE.controller;

import java.io.IOException;

import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shopPhone.shopphoneBE.entity.Image;
import com.shopPhone.shopphoneBE.model.ImageModel;
import com.shopPhone.shopphoneBE.model.MessageResponse;
import com.shopPhone.shopphoneBE.service.IImageService;

@CrossOrigin
@RestController
@RequestMapping("/api/images")
public class ImageController {
    
    private final IImageService imageService;

    @Autowired
    public ImageController(IImageService imageService){
        this.imageService = imageService;
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> createNewImage(@RequestParam("productId") int productId, @RequestParam("file") MultipartFile multipartFile, @RequestParam("type") String type) {
		ImageModel imageDTO = null;
		imageDTO = imageService.createImageOfProduct(productId, type,multipartFile);
		String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/image/")
				.path(imageDTO.getId() + "").toUriString();
		imageDTO.setUrl(url);
		return ResponseEntity.ok().body(imageDTO);
	}

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFileImageById(@PathVariable int id) {
		Image image = imageService.getImage(id);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileType() + "\"")
				.body(image.getFile());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteImage(@PathVariable int id){
		Boolean res = imageService.deleteImage(id);
		if(res){
			return ResponseEntity.ok().body(new MessageResponse("delete successfully!"));
		}else{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("delete faild");
		}
	}

	@DeleteMapping("/product/{productId}")
	public ResponseEntity<?> deleteImageByProductId(@PathVariable int productId){
		Boolean res = imageService.deleteImageByProductId(productId);
		if(res){
			return ResponseEntity.ok().body(new MessageResponse("delete successfully!"));
		}else{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("delete faild");
		}
	}
}
