package com.bk.leadManagement.LMS.service;

import com.bk.leadManagement.LMS.dto.LeadResponseDto;
import com.bk.leadManagement.LMS.model.Lead;
import com.bk.leadManagement.LMS.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public LeadServiceImpl(LeadRepository leadRepository, RestTemplate restTemplate) {
        this.leadRepository = leadRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public LeadResponseDto createLead(Lead lead) {
        // Save lead directly
        Lead savedLead = leadRepository.save(lead);

        // Convert saved lead to response DTO
        return new LeadResponseDto(
                savedLead.getId(),
                savedLead.getFirstName(),
                savedLead.getLastName(),
                savedLead.getEmail(),
                savedLead.getPhone(),
                savedLead.getCompany(),
                savedLead.getSource(),
                savedLead.getCustomFields()
        );
    }

    @Override
    public List<LeadResponseDto> getAllLeads() {
        List<Lead> leads = leadRepository.findAll();
        return leads.stream()
                .map(lead -> new LeadResponseDto(
                        lead.getId(),
                        lead.getFirstName(),
                        lead.getLastName(),
                        lead.getEmail(),
                        lead.getPhone(),
                        lead.getCompany(),
                        lead.getSource(),
                        lead.getCustomFields()))
                .collect(Collectors.toList());
    }

    @Override
    public LeadResponseDto getLeadById(Long leadId) {
        Optional<Lead> leadOptional = leadRepository.findById(leadId);
        if (leadOptional.isEmpty()) {
            throw new RuntimeException("❌ Lead not found with ID: " + leadId);
        }

        Lead lead = leadOptional.get();
        return new LeadResponseDto(
                lead.getId(),
                lead.getFirstName(),
                lead.getLastName(),
                lead.getEmail(),
                lead.getPhone(),
                lead.getCompany(),
                lead.getSource(),
                lead.getCustomFields()
        );
    }

    @Override
    public boolean convertLeadToCustomer(Long leadId) {
        Optional<Lead> leadOptional = leadRepository.findById(leadId);
        if (leadOptional.isEmpty()) {
            throw new RuntimeException("❌ Lead not found with ID: " + leadId);
        }

        Lead lead = leadOptional.get();

        // Prepare customer request body
        Map<String, Object> customerRequest = new HashMap<>();
        customerRequest.put("firstName", lead.getFirstName());
        customerRequest.put("lastName", lead.getLastName());
        customerRequest.put("email", lead.getEmail());
        customerRequest.put("phone", lead.getPhone());
        customerRequest.put("company", lead.getCompany());
        customerRequest.put("customFields", lead.getCustomFields());

        // Call Customer Management Service (CMS) to register the customer
        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(
                "http://localhost:8081/customers/register", customerRequest, String.class
            );
        } catch (Exception e) {
            System.err.println("❌ Lead conversion failed: " + e.getMessage());
            return false;
        }

        // If successful, delete lead
        if (response.getStatusCode().is2xxSuccessful()) {
            leadRepository.delete(lead);
            return true;
        }

        return false;
    }
}
