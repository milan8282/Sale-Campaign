package com.example.sale.Campaign.Management.schedulers;

import com.example.sale.Campaign.Management.models.Campaign;
import com.example.sale.Campaign.Management.models.CampaignDiscount;
import com.example.sale.Campaign.Management.models.Product;
import com.example.sale.Campaign.Management.services.CampaignService;
import com.example.sale.Campaign.Management.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CampaignScheduler {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private ProductService productService;

    @Scheduled(cron = "0 0 0 * * ?")  // Runs daily at midnight
    public void manageCampaigns() {
        LocalDate today = LocalDate.now();

        // Activate campaigns starting today
        List<Campaign> campaignsToActivate = campaignService.getCampaignsStartingToday(today);
        for (Campaign campaign : campaignsToActivate) {
            campaignService.applyCampaignDiscounts(campaign);
        }

        // Deactivate campaigns ending today
        List<Campaign> campaignsToDeactivate = campaignService.getCampaignsEndingToday(today);
        for (Campaign campaign : campaignsToDeactivate) {
            campaignService.removeCampaignDiscounts(campaign);
        }
    }
}

