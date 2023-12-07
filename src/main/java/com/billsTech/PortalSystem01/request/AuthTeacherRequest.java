package com.billsTech.PortalSystem01.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class AuthTeacherRequest {
    private String email;
    private String password;
}
