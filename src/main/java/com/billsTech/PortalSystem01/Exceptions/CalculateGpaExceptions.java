package com.billsTech.PortalSystem01.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculateGpaExceptions extends RuntimeException {
    private String message;
}
