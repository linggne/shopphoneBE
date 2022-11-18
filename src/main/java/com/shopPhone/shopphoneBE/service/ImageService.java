package com.shopPhone.shopphoneBE.service;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shopPhone.shopphoneBE.entity.Image;
import com.shopPhone.shopphoneBE.entity.Product;
import com.shopPhone.shopphoneBE.model.ImageModel;
import com.shopPhone.shopphoneBE.repository.ImageRepository;
import com.shopPhone.shopphoneBE.repository.ProductRepository;

@Service
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    @Autowired
    public ImageService(ImageRepository imageRepository, ModelMapper modelMapper, ProductRepository productRepository){
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @Override
    public ImageModel createImageOfProduct(int productId, String type, MultipartFile multipartFile) {
		try {
            Image image = new Image();
            Product product = productRepository.findById(productId).get();
            image.setProduct(product);
            image.setType(type);
            image.setFileType(multipartFile.getContentType());
            image.setFile(multipartFile.getBytes());
            this.imageRepository.save(image);
		    return modelMapper.map(image, ImageModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Image getImage(int id) {
        return imageRepository.findById(id).get();
    }

    @Override
    public Boolean deleteImage(int id) {
        try {
            imageRepository.deleteById(id);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteImageByProductId(int productId) {
        try {
            imageRepository.deleteAllByProductId(productId);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
}
