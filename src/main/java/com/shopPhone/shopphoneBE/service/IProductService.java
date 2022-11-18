package com.shopPhone.shopphoneBE.service;

import java.util.List;
import java.util.Map;

import com.shopPhone.shopphoneBE.model.ProductRequest;
import com.shopPhone.shopphoneBE.model.ProductResponse;

public interface IProductService {
    public ProductResponse createProduct(ProductRequest product);
    public List<ProductResponse> findAllProduct(Map<String, Object> filters);
    public Boolean deleteProduct(int productId);
    public Boolean updateProduct(ProductRequest productRequest, int productId);
    public ProductResponse getProductByID(int productId);
}
