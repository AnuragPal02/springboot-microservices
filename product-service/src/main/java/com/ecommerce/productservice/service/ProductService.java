package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductRequest;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(ProductRequest request, String userEmail) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCreatedBy(userEmail);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(Long id, ProductRequest request, String userEmail) {
        Product product = getProductById(id);

        if (!product.getCreatedBy().equals(userEmail)) {
            throw new RuntimeException("You can only update your own products");
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id, String userEmail, String role) {
        Product product = getProductById(id);

        if (!role.equals("ADMIN") && !product.getCreatedBy().equals(userEmail)) {
            throw new RuntimeException("Only ADMIN or product owner can delete");
        }

        productRepository.deleteById(id);
    }
}