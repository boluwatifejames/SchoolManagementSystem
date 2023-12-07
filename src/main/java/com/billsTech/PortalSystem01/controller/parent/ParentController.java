package com.billsTech.PortalSystem01.controller.parent;

import com.billsTech.PortalSystem01.entity.GeneralRecord;
import com.billsTech.PortalSystem01.request.AuthenticateUserRequest;
import com.billsTech.PortalSystem01.request.FetchRequest;
import com.billsTech.PortalSystem01.request.RegisterParentRequest;
import com.billsTech.PortalSystem01.response.*;
import com.billsTech.PortalSystem01.service.LoginAndSignUp_Service;
import com.billsTech.PortalSystem01.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;
    private final LoginAndSignUp_Service loginAndSignUpService;

    @PostMapping("/user/parent/register")
    public ResponseEntity<AuthenticateUserResponse> registerParent(@RequestBody RegisterParentRequest registerRequest) {
        return ResponseEntity.ok(loginAndSignUpService.registerParent(registerRequest));
    }

    @PostMapping("/user/parent/authenticate")
    public ResponseEntity<AuthenticateUserResponse> signInParent(@RequestBody AuthenticateUserRequest authenticateUserRequest) {
        return ResponseEntity.ok(loginAndSignUpService.signInUser(authenticateUserRequest));
    }

    @GetMapping("/parent/fetchResult")
    public List<FetchResultResponse> fetchResult(@RequestParam String matricNumber, @RequestBody FetchRequest fetchRequest) {
        return parentService.fetchResult(matricNumber, fetchRequest);
    }

    @PostMapping("/parent/calculateGpa")
    public CalculateGpaResponse calculateGpa(@RequestParam String matricNumber , @RequestBody FetchRequest fetchRequest){
        return parentService.calculateGpa(matricNumber,fetchRequest);
    }

    @GetMapping("/parent/fetchAllResults")
    public List<FetchAllResultsResponse> fetchAllResults(@RequestParam String matricNumber){
        return parentService.fetchAllResults(matricNumber);
    }

    @PostMapping("/parent/calCgpa")
    public ResponseEntity<CalculateCgpaResponse> calculateCGpa(@RequestParam String matricNumber){
        return ResponseEntity.ok(parentService.calculateCGpa(matricNumber));
    }

    @GetMapping("/parent/fetchComplaints")
    public  List<FetchComplaintsResponse> fetchComplaints(@RequestParam String matricNumber){
        return parentService.fetchComplaints(matricNumber);
    }
}
