package org.rhydo.microproduct.services;

import org.rhydo.microproduct.dtos.ProductRequest;
import org.rhydo.microproduct.dtos.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    void deleteProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);

    ProductResponse getProduct(Long id);
}
