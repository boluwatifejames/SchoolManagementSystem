package com.billsTech.PortalSystem01.controller.Admin;

import com.billsTech.PortalSystem01.request.RegisterUserRequest;
import com.billsTech.PortalSystem01.response.AuthenticateUserResponse;
import com.billsTech.PortalSystem01.service.LoginAndSignUp_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final LoginAndSignUp_Service loginAndSignUpService;


    @PostMapping("/Admin/register")
    public ResponseEntity<AuthenticateUserResponse> registerAdmin(@RequestBody RegisterUserRequest registerRequest) {
        return ResponseEntity.ok(loginAndSignUpService.registerAdmin(registerRequest));
    }
}
