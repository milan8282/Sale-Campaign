package com.example.sale.Campaign.Management.controllers;

import com.example.sale.Campaign.Management.models.PricingHistory;
import com.example.sale.Campaign.Management.models.Product;
import com.example.sale.Campaign.Management.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Get product details by ID
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Optional<Product> productOpt = productService.findProductById(productId);
        if (productOpt.isPresent()) {
            return new ResponseEntity<>(productOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.saveProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Get the pricing history of a product
    @GetMapping("/{productId}/pricing-history")
    public ResponseEntity<List<PricingHistory>> getPricingHistory(@PathVariable Long productId) {
        List<PricingHistory> pricingHistory = productService.getPricingHistory(productId);
        return new ResponseEntity<>(pricingHistory, HttpStatus.OK);
    }

    // Paginated Product API
    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(@RequestParam int page, @RequestParam int pageSize) {
        Page<Product> products = productService.getProductsPaginated(page, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
