package com.bk.leadManagement.LMS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bk.leadManagement.LMS.model.Lead;


public interface LeadRepository extends JpaRepository<Lead, Integer> {

	Optional<Lead> findById(Long leadId);

}
