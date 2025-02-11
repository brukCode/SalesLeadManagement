package com.bk.leadManagement.LMS.model;


import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "leads")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private String source; // Updated field (source → leadSource)
	public void setStatus(String string) {
		// TODO Auto-generated method stub
	}
	
	@Embedded
	CustomFields customFields;
}