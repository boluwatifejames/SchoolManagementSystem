package com.billsTech.PortalSystem01.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LodgeComplaintException extends RuntimeException {
    private String message;
}
