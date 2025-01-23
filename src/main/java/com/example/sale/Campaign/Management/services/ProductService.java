package com.example.sale.Campaign.Management.services;

import com.example.sale.Campaign.Management.models.PricingHistory;
import com.example.sale.Campaign.Management.models.Product;
import com.example.sale.Campaign.Management.repositories.PricingHistoryRepository;
import com.example.sale.Campaign.Management.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PricingHistoryRepository pricingHistoryRepository;

    // Find a product by its ID
    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }

    // Save a product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Save pricing history for a product
    public void savePricingHistory(Product product, Double price, Double discount) {
        PricingHistory pricingHistory = new PricingHistory();
        pricingHistory.setProduct(product);
        pricingHistory.setPrice(price);
        pricingHistory.setDiscount(discount);
        pricingHistory.setDate(java.time.LocalDate.now());
        pricingHistoryRepository.save(pricingHistory);
    }

    // Fetch pricing history of a product
    public List<PricingHistory> getPricingHistory(Long productId) {
        return pricingHistoryRepository.findPricingHistoryByProductId(productId);
    }

    // Paginated Product API
    public Page<Product> getProductsPaginated(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findAll(pageable);
    }
}
