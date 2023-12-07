package com.billsTech.PortalSystem01.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResultExceptions extends RuntimeException{
    private String message;
}
