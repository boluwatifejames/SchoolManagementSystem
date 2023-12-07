package com.billsTech.PortalSystem01.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterParentRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String wardsMatriculation_Number;
}
