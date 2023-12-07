package com.billsTech.PortalSystem01.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "COMPLAINTS_TABLE")
public class Complaints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String firstName;
    private String lastName;
    private String matricNumber;
    private String date ;
    private String complaintDescription;
    //New
    private String email;


}
