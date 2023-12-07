package com.billsTech.PortalSystem01.Exceptions;

import com.billsTech.PortalSystem01.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class ApiHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FetchResultExceptions.class)
    public ResponseEntity<FetchResultResponse> fetchResult(FetchResultExceptions exception) {
        return fetchResultException(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<FetchResultResponse> fetchResultException(HttpStatus httpStatus, FetchResultExceptions e) {
        return new ResponseEntity<>(
                new FetchResultResponse(null,null,null,null,null,null,null,null, e.getMessage()),
                httpStatus);
    }
    @ExceptionHandler(CalculateGpaExceptions.class)
    public ResponseEntity<CalculateGpaResponse> calGpa(CalculateGpaExceptions exception) {
        return calGpaException(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<CalculateGpaResponse> calGpaException(HttpStatus httpStatus, CalculateGpaExceptions e) {
        return new ResponseEntity<>(
                new CalculateGpaResponse(null,null, e.getMessage()),
                httpStatus);
    }
    @ExceptionHandler(FetchAllResultsExceptions.class)
    public ResponseEntity<FetchAllResultsResponse> fetchAll(FetchAllResultsExceptions exception) {
        return fetchAllException(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<FetchAllResultsResponse> fetchAllException(HttpStatus httpStatus, FetchAllResultsExceptions e) {
        return new ResponseEntity<>(
                new FetchAllResultsResponse(null,null,null,null,null,null,null,null, e.getMessage()),
                httpStatus);
    }
    @ExceptionHandler(CalculateCgpaExceptions.class)
    public ResponseEntity<CalculateCgpaResponse> calCgpa(CalculateCgpaExceptions exception) {
        return calCgpaException(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<CalculateCgpaResponse> calCgpaException(HttpStatus httpStatus, CalculateCgpaExceptions e) {
        return new ResponseEntity<>(
                new CalculateCgpaResponse(null, e.getMessage()),
                httpStatus);
    }
    @ExceptionHandler(FetchComplaintExceptions.class)
    public ResponseEntity<FetchComplaintsResponse> fetchComplaint(FetchComplaintExceptions exception) {
        return fetchComplaintException(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<FetchComplaintsResponse> fetchComplaintException(HttpStatus httpStatus, FetchComplaintExceptions e) {
        return new ResponseEntity<>(
                new FetchComplaintsResponse(null,null,null, e.getMessage()),
                httpStatus);
    }
    @ExceptionHandler(LodgeComplaintException.class)
    public ResponseEntity<LodgeComplaintResponse> LodgeComplaint(LodgeComplaintException exception) {
        return LodgeComplaintException(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<LodgeComplaintResponse> LodgeComplaintException(HttpStatus httpStatus, LodgeComplaintException e) {
        return new ResponseEntity<>(
                new LodgeComplaintResponse(null,null,null,null,null,null, e.getMessage()),
                httpStatus);
    }
    @ExceptionHandler(UploadResultExceptions.class)
    public ResponseEntity<UploadResultsResponse> uploadRes(UploadResultExceptions exception) {
        return uploadResException(HttpStatus.BAD_REQUEST, exception);
    }
    private ResponseEntity<UploadResultsResponse> uploadResException(HttpStatus httpStatus, UploadResultExceptions e) {
        return new ResponseEntity<>(
                new UploadResultsResponse(null, e.getMessage()),
                httpStatus);
    }
}
