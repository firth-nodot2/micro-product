package org.rhydo.microproduct.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.rhydo.microproduct.dtos.ProductRequest;
import org.rhydo.microproduct.dtos.ProductResponse;
import org.rhydo.microproduct.exceptions.ResourceNotFoundException;
import org.rhydo.microproduct.models.Product;
import org.rhydo.microproduct.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);

        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setCategory(product.getCategory());
                    existingProduct.setStockQuantity(product.getStockQuantity());
                    existingProduct.setImageUrl(product.getImageUrl());

                    Product updatedProduct = productRepository.save(existingProduct);
                    return modelMapper.map(updatedProduct, ProductResponse.class);
                }).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(existingProduct -> modelMapper.map(existingProduct, ProductResponse.class))
                .toList();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(existingProduct -> modelMapper.map(existingProduct, ProductResponse.class))
                .toList();
    }

    @Override
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return modelMapper.map(product, ProductResponse.class);
    }
}
