package com.bk.leadManagement.LMS.controller;


import com.bk.leadManagement.LMS.dto.LeadResponseDto;
import com.bk.leadManagement.LMS.model.Lead;
import com.bk.leadManagement.LMS.service.LeadService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/leads")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    // Create a new lead
    @PostMapping("/create")
    public ResponseEntity<LeadResponseDto> createLead(@RequestBody Lead leadInfo) {
        LeadResponseDto createdLead = leadService.createLead(leadInfo);
        return ResponseEntity.ok(createdLead);
    }

    // Convert a lead to a customer
    @PutMapping("/convert/{leadId}")
    public ResponseEntity<String> convertLead(@PathVariable Long leadId) {
        boolean isConverted = leadService.convertLeadToCustomer(leadId);
        if (isConverted) {
            return ResponseEntity.ok("Lead successfully converted to customer.");
        } else {
            return ResponseEntity.badRequest().body("Lead conversion failed.");
        }
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<List<LeadResponseDto>> getAllLeads() {
       // log.info("📋 Fetching all leads...");
        List<LeadResponseDto> leads = leadService.getAllLeads();
        return ResponseEntity.ok(leads);
    }
    
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllLeads() {
        boolean deleted = leadService.deleteAllLeads();
        if (deleted) {
            return ResponseEntity.ok("✅ All leads deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("❌ No leads found to delete.");
        }
    }


}
