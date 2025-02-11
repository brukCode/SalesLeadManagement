package com.bk.leadManagement.LMS.service;

import com.bk.leadManagement.LMS.dto.LeadResponseDto;
import com.bk.leadManagement.LMS.model.Lead;

import java.util.List;

public interface LeadService {
    LeadResponseDto createLead(Lead lead); // Create a new lead
    List<LeadResponseDto> getAllLeads(); // Get all leads
    LeadResponseDto getLeadById(Long leadId); // Get lead by ID
    boolean convertLeadToCustomer(Long leadId); // Convert a lead to a customer
    boolean deleteAllLeads();
}
