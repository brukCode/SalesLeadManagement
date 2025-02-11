package com.bk.leadManagement.LMS.dto;

import com.bk.leadManagement.LMS.model.CustomFields;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private String source;
    private CustomFields customFields;
}

