package com.billsTech.PortalSystem01.request;

import lombok.Data;

@Data
public class ComplaintRequest {
    private String firstName;
    private String lastName;
    private String matricNumber;
    private String date ;
    private String level;
    private String semester;
    private String complaintDescription;
    //New
    private String email;


}
