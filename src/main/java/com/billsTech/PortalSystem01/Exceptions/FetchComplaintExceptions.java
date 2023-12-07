package com.billsTech.PortalSystem01.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchComplaintExceptions extends RuntimeException {
    private String message;
}
