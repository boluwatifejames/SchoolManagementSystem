package com.billsTech.PortalSystem01.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LodgeComplaintResponse {
    Long id;
    private String firstName;
    private String lastName;
    private String matricNumber;
    private String date ;
    private String complaintDescription;
    private String email;
    private String message;

    public LodgeComplaintResponse(Long id, String firstName, String lastName, String matricNumber, String date, String complaintDescription, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.matricNumber = matricNumber;
        this.date = date;
        this.complaintDescription = complaintDescription;
        this.email = email;
    }


}
