package com.billsTech.PortalSystem01.service;

import com.billsTech.PortalSystem01.auth.Roles;
import com.billsTech.PortalSystem01.auth.config.JwtService;
import com.billsTech.PortalSystem01.entity.Users;
import com.billsTech.PortalSystem01.repositories.UserRepo;
import com.billsTech.PortalSystem01.repositories.UserRepo2;
import com.billsTech.PortalSystem01.request.*;
import com.billsTech.PortalSystem01.response.AuthTeacherResponse;
import com.billsTech.PortalSystem01.response.AuthenticateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginAndSignUp_Service {

    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final UserRepo2 userRepo2;
    private final AuthenticationManager authenticationManager;
    public AuthenticateUserResponse registerUser(RegisterUserRequest request) {
        var user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .matricNumber(request.getMatricNumber())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Roles.STUDENT)
                .build();
        user = userRepo.save(user);
        var user2 = userRepo2.save(user);
        return AuthenticateUserResponse.builder()
                .Status("Registered Successfully")
                .build();
    }
    public AuthenticateUserResponse registerParent(RegisterParentRequest request) {
        var user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .wardsMatriculation_Number(request.getWardsMatriculation_Number())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Roles.PARENT)
                .build();
        user = userRepo.save(user);
        var user2 = userRepo2.save(user);
        return AuthenticateUserResponse.builder()
                .Status("Registered Successfully")
                .build();
    }

    public AuthenticateUserResponse registerAdmin(RegisterUserRequest request) {
        var user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .matricNumber(request.getMatricNumber())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Roles.ADMIN)
                .build();
        user = userRepo.save(user);
        return AuthenticateUserResponse.builder()
                .Status("Registered Successfully")
                .build();
    }


    public AuthenticateUserResponse signInUser(AuthenticateUserRequest authenticateUserRequest) {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken
                        (
                                authenticateUserRequest.getEmail(),
                                authenticateUserRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        return AuthenticateUserResponse.builder()
                .token(token)
                .build();
    }

    public AuthTeacherResponse registerTeacher(RegisterTeacherRequest request) {
        var user =Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .lecturerId(request.getLecturerId())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Roles.LECTURER)
                .build();
        user = userRepo.save(user);
        return AuthTeacherResponse.builder()
                .Status("Registered Successfully")
                .build();
    }

    public AuthTeacherResponse authenticateTeacher(AuthTeacherRequest authTeacherRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authTeacherRequest.getEmail(),
                authTeacherRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtService.generateToken(authentication);
        return AuthTeacherResponse.builder()
                .token(token)
                .build();
    }





    @Transactional(readOnly = true)
    private Users getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("No User not found with such email"));
    }

}
