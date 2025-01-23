package com.example.sale.Campaign.Management.controllers;

import com.example.sale.Campaign.Management.models.Campaign;
import com.example.sale.Campaign.Management.services.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    // Get all campaigns
    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        List<Campaign> campaigns = campaignService.getAllCampaigns();
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    // Get active campaigns
    @GetMapping("/active")
    public ResponseEntity<List<Campaign>> getActiveCampaigns(@RequestParam("date") String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<Campaign> activeCampaigns = campaignService.getCurrentCampaigns(parsedDate);
        return new ResponseEntity<>(activeCampaigns, HttpStatus.OK);
    }

    // Get past campaigns
    @GetMapping("/past")
    public ResponseEntity<List<Campaign>> getPastCampaigns() {
        LocalDate today = LocalDate.now();
        List<Campaign> pastCampaigns = campaignService.getPastCampaigns(today);
        return new ResponseEntity<>(pastCampaigns, HttpStatus.OK);
    }

    // Get upcoming campaigns
    @GetMapping("/upcoming")
    public ResponseEntity<List<Campaign>> getUpcomingCampaigns() {
        LocalDate today = LocalDate.now();
        List<Campaign> upcomingCampaigns = campaignService.getUpcomingCampaigns(today);
        return new ResponseEntity<>(upcomingCampaigns, HttpStatus.OK);
    }

    // Create a new campaign
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        Campaign createdCampaign = campaignService.createCampaign(campaign);
        return new ResponseEntity<>(createdCampaign, HttpStatus.CREATED);
    }


    // Apply discounts for a campaign
    @PostMapping("/{campaignId}/apply-discounts")
    public ResponseEntity<String> applyDiscounts(@PathVariable Long campaignId) {
        Campaign campaign = campaignService.getCampaignById(campaignId);
        if (campaign == null) {
            return new ResponseEntity<>("Campaign not found", HttpStatus.NOT_FOUND);
        }
        campaignService.applyCampaignDiscounts(campaign);
        return new ResponseEntity<>("Discounts applied successfully", HttpStatus.OK);
    }

    // Remove discounts when campaign ends
    @PostMapping("/{campaignId}/remove-discounts")
    public ResponseEntity<String> removeDiscounts(@PathVariable Long campaignId) {
        Campaign campaign = campaignService.getCampaignById(campaignId);
        if (campaign == null) {
            return new ResponseEntity<>("Campaign not found", HttpStatus.NOT_FOUND);
        }
        campaignService.removeCampaignDiscounts(campaign);
        return new ResponseEntity<>("Discounts removed successfully", HttpStatus.OK);
    }
}
