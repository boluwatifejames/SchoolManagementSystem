package com.billsTech.PortalSystem01.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;


@SuperBuilder
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String developerMessage;
    protected String oath;
    protected String requestMethod;
    protected Map<?,?> data;



}
