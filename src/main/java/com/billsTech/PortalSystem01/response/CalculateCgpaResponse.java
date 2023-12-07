package com.billsTech.PortalSystem01.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculateCgpaResponse {
    private Double cgpa;
    private String message;
    public CalculateCgpaResponse(Double cgpa){
        this.cgpa = cgpa;
    }
}
