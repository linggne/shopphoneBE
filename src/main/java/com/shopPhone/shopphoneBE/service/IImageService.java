package com.shopPhone.shopphoneBE.service;

import org.springframework.web.multipart.MultipartFile;

import com.shopPhone.shopphoneBE.entity.Image;
import com.shopPhone.shopphoneBE.model.ImageModel;

public interface IImageService {
    public ImageModel createImageOfProduct(int productId,String type,MultipartFile multipartFile);
    public Image getImage(int id);
    public Boolean deleteImage(int id);
    public Boolean deleteImageByProductId(int productId);
}
