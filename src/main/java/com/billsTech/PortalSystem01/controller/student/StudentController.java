package com.billsTech.PortalSystem01.controller.student;

import com.billsTech.PortalSystem01.entity.GeneralRecord;
import com.billsTech.PortalSystem01.request.AuthenticateUserRequest;
import com.billsTech.PortalSystem01.request.FetchRequest;
import com.billsTech.PortalSystem01.request.RegisterUserRequest;
import com.billsTech.PortalSystem01.response.*;
import com.billsTech.PortalSystem01.service.LoginAndSignUp_Service;
import com.billsTech.PortalSystem01.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {
   private final StudentService studentService;

    @Autowired
    private LoginAndSignUp_Service loginAndSignUpService;

    @PostMapping("/user/student/register")
    public ResponseEntity<AuthenticateUserResponse> registerUser(@RequestBody RegisterUserRequest registerRequest) {
        return ResponseEntity.ok(loginAndSignUpService.registerUser(registerRequest));
    }

    @PostMapping("/user/student/authenticate")
    public ResponseEntity<AuthenticateUserResponse> signInUser(@RequestBody AuthenticateUserRequest authenticateUserRequest) {
        return ResponseEntity.ok(loginAndSignUpService.signInUser(authenticateUserRequest));
    }

    @GetMapping("/student/fetchResult")
    public List<FetchResultResponse> fetchResult(@RequestParam String matricNumber, @RequestBody FetchRequest fetchRequest) {
        return studentService.fetchResult(matricNumber, fetchRequest);
    }
    @PostMapping("/student/calculateGpa")
    public ResponseEntity<CalculateGpaResponse> calculateGpa(@RequestParam String matricNumber , @RequestBody FetchRequest fetchRequest){
        return ResponseEntity.ok(studentService.calculateGpa(matricNumber,fetchRequest));
    }

    @GetMapping("/student/fetchAllResults")
    public List<FetchAllResultsResponse> fetchAllResults(@RequestParam String matricNumber){
        return studentService.fetchAllResults(matricNumber);
    }

    @PostMapping("/student/calCgpa")
    public ResponseEntity<CalculateCgpaResponse> calculateCGpa(@RequestParam String matricNumber){
        return ResponseEntity.ok(studentService.calculateCGpa(matricNumber));
    }

    @GetMapping("/student/fetchComplaints")
    public  List<FetchComplaintsResponse> fetchComplaints(@RequestParam String matricNumber){
        return studentService.fetchComplaints(matricNumber);
    }
}