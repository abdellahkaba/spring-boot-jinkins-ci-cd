package com.isi.service;

import com.isi.dto.ProductRequest;
import com.isi.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse newProduct(ProductRequest request);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProduct();
    ProductResponse updateProduct(ProductRequest request);
    void deleteProductById(Long id);
}
