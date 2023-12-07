package com.billsTech.PortalSystem01.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FetchComplaintsResponse {
   private String fullName;
    private String date;
    private String complaintDescription;
    private String message;
}
