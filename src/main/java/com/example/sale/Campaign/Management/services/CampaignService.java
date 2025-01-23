package com.example.sale.Campaign.Management.services;

import com.example.sale.Campaign.Management.models.Campaign;
import com.example.sale.Campaign.Management.models.CampaignDiscount;
import com.example.sale.Campaign.Management.models.Product;
import com.example.sale.Campaign.Management.repositories.CampaignRepository;
import com.example.sale.Campaign.Management.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;



@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductRepository productRepository;

    // Fetch all campaigns
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    // Fetch campaigns that start on a given date
    public List<Campaign> getCampaignsStartingToday(LocalDate today) {
        return campaignRepository.findByStartDate(today);
    }

    // Fetch campaigns that end on a given date
    public List<Campaign> getCampaignsEndingToday(LocalDate today) {
        return campaignRepository.findByEndDate(today);
    }

    // Fetch currently active campaigns
    public List<Campaign> getCurrentCampaigns(LocalDate currentDate) {
        return campaignRepository.findCurrentCampaigns(currentDate);
    }

    // Fetch past campaigns (end date before today)
    public List<Campaign> getPastCampaigns(LocalDate today) {
        return campaignRepository.findByEndDateBefore(today);
    }

    // Fetch upcoming campaigns (start date after today)
    public List<Campaign> getUpcomingCampaigns(LocalDate today) {
        return campaignRepository.findByStartDateAfter(today);
    }

    // Create a new campaign
    public Campaign createCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    // Apply the discounts for the campaign on products
    public void applyCampaignDiscounts(Campaign campaign) {
        for (CampaignDiscount discount : campaign.getCampaignDiscounts()) {
            Product product = discount.getProduct();
            product.setCurrentPrice(product.getCurrentPrice() * (1 - discount.getDiscount() / 100));
            productRepository.save(product);
        }
    }

    // Remove the discounts when the campaign ends
    public void removeCampaignDiscounts(Campaign campaign) {
        for (CampaignDiscount discount : campaign.getCampaignDiscounts()) {
            Product product = discount.getProduct();
            product.setCurrentPrice(product.getMrp());
            productRepository.save(product);
        }
    }

    // Get a campaign by ID
    public Campaign getCampaignById(Long campaignId) {
        return campaignRepository.findById(campaignId).orElse(null);
    }
}

