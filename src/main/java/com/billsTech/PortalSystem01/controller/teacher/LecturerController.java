package com.billsTech.PortalSystem01.controller.teacher;

import com.billsTech.PortalSystem01.domain.HttpResponse;
import com.billsTech.PortalSystem01.entity.Complaints;
import com.billsTech.PortalSystem01.entity.GeneralRecord;
import com.billsTech.PortalSystem01.entity.TeacherRecord;
import com.billsTech.PortalSystem01.request.AuthTeacherRequest;
import com.billsTech.PortalSystem01.request.ComplaintRequest;
import com.billsTech.PortalSystem01.request.RegisterTeacherRequest;
import com.billsTech.PortalSystem01.response.AuthTeacherResponse;
import com.billsTech.PortalSystem01.response.LodgeComplaintResponse;
import com.billsTech.PortalSystem01.service.LecturerService;
import com.billsTech.PortalSystem01.service.LoginAndSignUp_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class LecturerController {
    @Autowired
    private LoginAndSignUp_Service loginAndSignUpService;
    private final LecturerService lecturerService;

    @PostMapping("/user/lecturer/register")
    public ResponseEntity<AuthTeacherResponse> registerTeacher(@RequestBody RegisterTeacherRequest registerRequest) {
        return ResponseEntity.ok(loginAndSignUpService.registerTeacher(registerRequest));
    }
    @PostMapping("/user/lecturer/authenticate")
    public ResponseEntity<AuthTeacherResponse> authenticateTeacher(@RequestBody AuthTeacherRequest authTeacherRequest){
        return ResponseEntity.ok(loginAndSignUpService.authenticateTeacher(authTeacherRequest));
    }


    @PostMapping("/lecturer/uploadResult")
    public void uploadResult(@RequestBody List<TeacherRecord> record){
        lecturerService.uploadResult(record);
    }

    @PostMapping("/lecturer/lodgeComplaint")
    public ResponseEntity<HttpResponse> sendComplaint(@RequestBody ComplaintRequest complaintRequest){
        LodgeComplaintResponse r = lecturerService.sendComplaint(complaintRequest);
        return ResponseEntity.created(URI.create(""))
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("complaint : ",complaintRequest))
                        .developerMessage(complaintRequest.getComplaintDescription())
                        .status(HttpStatus.CREATED)
                        .statusCode(CREATED.value())
                        .build()
                );
    }

//To be done :
    @GetMapping("/lecturer/fetchAllUploadedResults")
    public List<GeneralRecord> fetchAllUploadedResults(){
        return null;
    }
    @GetMapping("/lecturer/fetchUploadedResult")
    public List<GeneralRecord> fetchUploadedResult(){
        return null;
    }

}
