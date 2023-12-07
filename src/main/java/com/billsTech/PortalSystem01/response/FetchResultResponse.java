package com.billsTech.PortalSystem01.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FetchResultResponse {
    private Long id;
    private String matricNumber;
    private String level;
    private String semester;
    private String courseName;
    private String courseCode;
    private Double courseUnit;
    private Double courseScore;
    private String message;
}
