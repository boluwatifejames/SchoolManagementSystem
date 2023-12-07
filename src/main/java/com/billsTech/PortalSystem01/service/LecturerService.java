package com.billsTech.PortalSystem01.service;

import com.billsTech.PortalSystem01.Exceptions.LodgeComplaintException;
import com.billsTech.PortalSystem01.Exceptions.UploadResultExceptions;
import com.billsTech.PortalSystem01.entity.Complaints;
import com.billsTech.PortalSystem01.entity.GeneralRecord;
import com.billsTech.PortalSystem01.entity.TeacherRecord;
import com.billsTech.PortalSystem01.entity.Users;
import com.billsTech.PortalSystem01.repositories.*;
import com.billsTech.PortalSystem01.request.ComplaintRequest;
import com.billsTech.PortalSystem01.response.LodgeComplaintResponse;
import com.billsTech.PortalSystem01.response.UploadResultsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LecturerService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    GeneralRecordsRepo generalRecordsRepo;
    @Autowired
    UploadRepo uploadRepo;

    @Autowired
    ComplaintRepo complaintRepo;

   private final EmailService emailService;


    public UploadResultsResponse uploadResult(List<TeacherRecord> record) {
        try {
            Users users = getCurrentUser();
            if (users.getRole().name().equals("LECTURER")) {
                uploadRepo.saveAll(record);
                for (TeacherRecord record1 : record) {
                    GeneralRecord generalRecord = new GeneralRecord();
                    generalRecord.setMatricNumber(record1.getMatricNumber());
                    generalRecord.setLevel(record1.getLevel());
                    generalRecord.setCourseName(record1.getCourseName());
                    generalRecord.setSemester(record1.getSemester());
                    generalRecord.setCourseCode(record1.getCourseCode());
                    generalRecord.setCourseUnit(record1.getCourseUnit());
                    generalRecord.setCourseCode(record1.getCourseCode());
                    generalRecord.setCourseScore(record1.getCourseScore());
                    generalRecordsRepo.save(generalRecord);
                    return UploadResultsResponse.builder()
                            .Status("Uploaded Successfully")
                            .build();
                }
            } else {
                return new UploadResultsResponse(null, "Unable to Find User");
            }
        }catch (IllegalArgumentException e) {
                throw new UploadResultExceptions(e.getMessage());
            }
        return UploadResultsResponse.builder()
                .Status("Result Uploaded")
                .build();
    }



    public LodgeComplaintResponse sendComplaint(ComplaintRequest complaintRequest) {
        try {
            Users users = getCurrentUser();
            if (users == null){
                throw new AccessDeniedException("invalid Or Expired Token");
            }
            if (!users.getRole().name().equals("LECTURER")) {
                throw new AccessDeniedException("Unauthorized access for non-lecturers");
            }
                Complaints complaints = new Complaints();
                complaints.setFirstName(complaintRequest.getFirstName());
                complaints.setLastName(complaintRequest.getLastName());
                complaints.setMatricNumber(complaintRequest.getMatricNumber());
                complaints.setDate(complaintRequest.getDate());
                complaints.setComplaintDescription(complaintRequest.getComplaintDescription());
                complaints.setEmail(complaintRequest.getEmail());
                emailService.sendSimpleEmailMessage(complaintRequest.getFirstName(), complaintRequest.getLastName(), complaintRequest.getEmail(), complaintRequest.getMatricNumber());
                complaintRepo.save(complaints);
           //     return complaints;
                return new LodgeComplaintResponse(complaints.getId(),complaints.getFirstName(),complaints
                        .getLastName(),complaints.getMatricNumber(),complaints.getDate(),complaints.getComplaintDescription(),complaints.getEmail());
        }
        catch (AccessDeniedException e){
            throw new LodgeComplaintException(e.getMessage());
        }
    }
    @Transactional(readOnly = true)
    private Users getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("No User not found with such email"));
    }
}
