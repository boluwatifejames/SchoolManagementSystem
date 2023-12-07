package com.billsTech.PortalSystem01.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class TeacherRecord implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String matricNumber;
    String level;
    String semester;
    String courseName;
    String courseCode;
    Double courseUnit;
    Double courseScore;
}
