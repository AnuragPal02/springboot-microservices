package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductRequest;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductRequest request,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail) {
        try {
            if (userEmail == null) {
                return ResponseEntity.badRequest().body("Authentication required");
            }
            Product product = productService.createProduct(request, userEmail);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail) {
        try {
            if (userEmail == null) {
                return ResponseEntity.badRequest().body("Authentication required");
            }
            Product product = productService.updateProduct(id, request, userEmail);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
            @RequestHeader(value = "X-User-Role", required = false) String role) {
        try {
            if (userEmail == null || role == null) {
                return ResponseEntity.badRequest().body("Authentication required");
            }
            productService.deleteProduct(id, userEmail, role);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}