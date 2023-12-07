package com.billsTech.PortalSystem01.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculateGpaResponse {
    private String semester;
    private Double gpa;
    private String message;
    public CalculateGpaResponse(String semester ,Double gpa){
        this.semester = semester;
        this.gpa = gpa;
    }
}
