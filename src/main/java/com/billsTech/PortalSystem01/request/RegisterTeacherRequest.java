package com.billsTech.PortalSystem01.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterTeacherRequest {
    private String firstName;
    private String lastName;
    private String lecturerId;
    private String email;
    private String password;
}
