package com.billsTech.PortalSystem01.entity;

import com.billsTech.PortalSystem01.auth.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private  String lastName;
    private String matricNumber;
    private String lecturerId;
    private String wardsMatriculation_Number;
    @Column(unique = true)
    private  String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles role ;
}
