package com.example.sale.Campaign.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaleCampaignManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaleCampaignManagementApplication.class, args);
	}

}
