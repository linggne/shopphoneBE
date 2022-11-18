package com.shopPhone.shopphoneBE.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shopPhone.shopphoneBE.entity.Brand;
import com.shopPhone.shopphoneBE.entity.Image;
import com.shopPhone.shopphoneBE.entity.Product;
import com.shopPhone.shopphoneBE.model.ImageModel;
import com.shopPhone.shopphoneBE.model.ProductRequest;
import com.shopPhone.shopphoneBE.model.ProductResponse;
import com.shopPhone.shopphoneBE.repository.BrandRepository;
import com.shopPhone.shopphoneBE.repository.ImageRepository;
import com.shopPhone.shopphoneBE.repository.ProductRepository;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final BrandRepository brandRepository;
    private final ImageRepository imageRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, BrandRepository brandRepository, ImageRepository imageRepository){
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productReq) {
        productReq.setDelete(false);
        Product product =  productRepository.save(mapToEntity(productReq));
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> findAllProduct(Map<String, Object> filters) {
        List<Product> products = new ArrayList<>();
        if(filters.get("brandId") != null && !filters.get("brandId").toString().isEmpty()){
            Brand brand = brandRepository.findById(Integer.parseInt((String)filters.get("brandId"))).get();
            products = productRepository.findAllByBrandAndIsDeleted(brand,false);
        }
        else if (filters.get("status") != null && !filters.get("status").toString().isEmpty()){
            products = productRepository.findAllByStatusAndIsDeleted(filters.get("status").toString(), false);
        }
        else if (filters.get("type") != null && !filters.get("type").toString().isEmpty()){
            products = productRepository.findAllByTypeAndIsDeleted(filters.get("type").toString(), false);
        }
        else {
            products = productRepository.findByIsDeleted(false);
        }
        List<ProductResponse> list = new ArrayList<>();
        products.forEach(item -> {
            ProductResponse product = modelMapper.map(item, ProductResponse.class);
            List<Image> images = imageRepository.findAllByProductId(item.getId());
            List<ImageModel> listImage = new ArrayList<>();
            images.forEach(image -> {
                String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/images/")
				.path(image.getId() + "").toUriString();
                ImageModel imageModel = modelMapper.map(image, ImageModel.class);
                imageModel.setUrl(url);
                listImage.add(imageModel);
            });
            product.setImage(listImage);
            list.add(product);
        });
        return list;
    }

    private Product mapToEntity(ProductRequest productRequest) {
		Product product = modelMapper.map(productRequest, Product.class);
		Brand brand = this.brandRepository.findById(productRequest.getBrandId()).get();
        product.setBrand(brand);
		return product;
	}

    @Override
    public Boolean deleteProduct(int productId) {
        try{
            Product product = productRepository.findById(productId).get();
            product.setDeleted(true);
            productRepository.save(product);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateProduct(ProductRequest productRequest, int productId) {
        Product productOld = productRepository.findById(productId).get();
        if(productOld != null){
            Product product =  mapToEntity(productRequest);
            product.setId(productId);
            productRepository.save(product);
            return true;
        }
        return false;
    }

    @Override
    public ProductResponse getProductByID(int productId) {
        Product product= productRepository.findById(productId).get();
        List<Image> images = imageRepository.findAllByProductId(product.getId());
        List<ImageModel> listImage = new ArrayList<>();
        images.forEach(image -> {
            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/images/")
            .path(image.getId() + "").toUriString();
            ImageModel imageModel = modelMapper.map(image, ImageModel.class);
            imageModel.setUrl(url);
            listImage.add(imageModel);
        });
        ProductResponse productDto = modelMapper.map(product, ProductResponse.class);
        productDto.setImage(listImage);
        return productDto;
    }
    
}
