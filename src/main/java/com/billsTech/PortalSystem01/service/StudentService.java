package com.billsTech.PortalSystem01.service;

import com.billsTech.PortalSystem01.Exceptions.*;
import com.billsTech.PortalSystem01.auth.Roles;
import com.billsTech.PortalSystem01.entity.GeneralRecord;
import com.billsTech.PortalSystem01.entity.Users;
import com.billsTech.PortalSystem01.repositories.*;
import com.billsTech.PortalSystem01.request.FetchRequest;
import com.billsTech.PortalSystem01.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserRepo2 userRepo2;
    @Autowired
    GeneralRecordsRepo generalRecordsRepo;
    @Autowired
    ComplaintRepo complaintRepo;

    public List<FetchResultResponse> fetchResult(String matricNumber, FetchRequest fetchRequest) {
        try {
            Users users = getCurrentUser();
            String studentMatricNumber = getStudentMatriculationNumberForDatabase(users);
                if (users.getRole().name().equals("STUDENT")){
                    if(!matricNumber.equals(studentMatricNumber)) {
                    throw new AccessDeniedException("You are not authorized to access this student's records.");
                }
                List<GeneralRecord> result = generalRecordsRepo.findByMatricNumber(matricNumber);
                return result.stream()
                        .filter(record -> fetchRequest.getLevel().equals(record.getLevel()) && fetchRequest.getSemester().equals(record.getSemester()))
                        .map(this::mapToFetchResultResponse)
                        .collect(Collectors.toList());
            }
                else {
                    throw new AccessDeniedException("Unauthorized Access");
                }
        }
        catch (IllegalArgumentException e){
            throw new FetchResultExceptions(e.getMessage());
        }
    }

    private FetchResultResponse mapToFetchResultResponse(GeneralRecord generalRecord) {
        FetchResultResponse response = new FetchResultResponse();
        // Map fields from GeneralRecord to FetchResultResponse
        response.setId(generalRecord.getId());
        response.setMatricNumber(generalRecord.getMatricNumber());
        response.setLevel(generalRecord.getSemester());
        response.setSemester(generalRecord.getSemester());
        response.setCourseName(generalRecord.getCourseName());
        response.setCourseCode(generalRecord.getCourseCode());
        response.setCourseUnit(generalRecord.getCourseUnit());
        response.setCourseScore(generalRecord.getCourseScore());
        return response;
    }
    private FetchAllResultsResponse mapToFetchAllResultResponse(GeneralRecord generalRecord) {
        FetchAllResultsResponse response = new FetchAllResultsResponse();
        // Map fields from GeneralRecord to FetchAllResultsResponse
        response.setId(generalRecord.getId());
        response.setMatricNumber(generalRecord.getMatricNumber());
        response.setLevel(generalRecord.getSemester());
        response.setSemester(generalRecord.getSemester());
        response.setCourseName(generalRecord.getCourseName());
        response.setCourseCode(generalRecord.getCourseCode());
        response.setCourseUnit(generalRecord.getCourseUnit());
        response.setCourseScore(generalRecord.getCourseScore());
        return response;
    }




    public CalculateGpaResponse calculateGpa(String matricNumber, FetchRequest fetchRequest) {
        try {
            Users users = getCurrentUser();
            String studentMatricNumber = getStudentMatriculationNumberForDatabase(users);
            if (users.getRole().name().equals("STUDENT")){
                if(!matricNumber.equals(studentMatricNumber)) {
                    throw new AccessDeniedException("You are not authorized to access this student's records.");
                }
                List<GeneralRecord> records = generalRecordsRepo
                        .findByMatricNumberAndLevelAndSemester(matricNumber, fetchRequest.getLevel(), fetchRequest.getSemester());
                if (!records.isEmpty()) {
                    double totalPoints = records.stream()
                            .mapToDouble(record -> convertScoreToPoints(record.getCourseScore()) * record.getCourseUnit())
                            .sum();
                    double totalUnits = records.stream().mapToDouble(GeneralRecord::getCourseUnit).sum();
                    if (totalUnits == 0.0) {
                        throw new IllegalArgumentException("Error: Total Unit is Zero");
                    }
                    // Calculate GPA
                    // Save or perform any other actions with the calculated GPA
                    // ...
                    double gpa =  totalPoints / totalUnits;
                    return new CalculateGpaResponse(fetchRequest.getSemester(),gpa);
                } else {
                    throw new IllegalArgumentException("No records found for the provided Matriculation Number, level or semester");
                }
            } else {
                throw new AccessDeniedException("Unauthorised");
            }
        }catch (IllegalArgumentException e){
            throw new CalculateGpaExceptions(e.getMessage());
        }

    }
    public List<FetchAllResultsResponse> fetchAllResults(String matricNumber) {
        try {
            Users users = getCurrentUser();
            String studentMatricNumber = getStudentMatriculationNumberForDatabase(users);
            if (users.getRole().name().equals("STUDENT")){
                if(!matricNumber.equals(studentMatricNumber)) {
                    throw new AccessDeniedException("You are not authorized to access this student's records.");
                }
                List<GeneralRecord> result = generalRecordsRepo.findByMatricNumber(matricNumber);
                return result.stream()
                        .map(this::mapToFetchAllResultResponse)
                        .collect(Collectors.toList());
            } else {
                throw new IllegalArgumentException("No records found for the provided matricNumber, level, and semester");
            }
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public CalculateCgpaResponse calculateCGpa(String matricNumber) {
        try {
            Users users = getCurrentUser();
            String studentMatricNumber = getStudentMatriculationNumberForDatabase(users);
            if (users.getRole().name().equals("STUDENT")){
                if(!matricNumber.equals(studentMatricNumber)) {
                    throw new AccessDeniedException("You are not authorized to access this student's records.");
                }
                List<GeneralRecord> records = generalRecordsRepo
                        .findByMatricNumber(matricNumber);
                if (!records.isEmpty()) {
                    double totalPoints = records.stream()
                            .mapToDouble(record -> convertScoreToPoints(record.getCourseScore()) * record.getCourseUnit())
                            .sum();
                    double totalUnits = records.stream().mapToDouble(GeneralRecord::getCourseUnit).sum();
                    if (totalUnits == 0.0) {
                        throw new IllegalArgumentException("Error: Total Unit is Zero");
                    }
                    // Calculate CGPA
                    // Save or perform any other actions with the calculated GPA
                    // ...
                  Double cgpa = totalPoints / totalUnits;
                    return new CalculateCgpaResponse(cgpa);
                } else {
                    throw new IllegalArgumentException("No records found for the provided matricNumber, level, and semester");
                }
            } else {
                throw new AccessDeniedException("Unauthorised");
            }
        }catch (IllegalArgumentException e){
            throw new CalculateCgpaExceptions(e.getMessage());
        }
    }
    public List<FetchComplaintsResponse> fetchComplaints(String matricNumber) {
        try {
            Users users = getCurrentUser();
            String studentMatricNumber = getStudentMatriculationNumberForDatabase(users);
            if (users.getRole().name().equals("STUDENT")){
                if(!matricNumber.equals(studentMatricNumber)) {
                    throw new AccessDeniedException("You are not authorized to access this student's records.");
                }
                List<Object[]> result = complaintRepo.findByMatricNumber(matricNumber);
                List<FetchComplaintsResponse> formattedDetails = new ArrayList<>();

                for (Object[] row : result) {
                    FetchComplaintsResponse response = new FetchComplaintsResponse();
                     response.setFullName("Full Name : "+ row[0] + " " + row[1]); // Concatenate firstName and lastName
                    response.setDate("Date : "+ LocalDateTime.now());//convert date to string
                    response.setComplaintDescription("Complaint Report : "+ row[2].toString()); // Convert the complaint description to String
                    formattedDetails.add(response);
                }
                return formattedDetails;
            } else {
                throw new IllegalArgumentException("No records found for the provided matricNumber, level, and semester");
            }
        } catch (IllegalArgumentException e) {
             throw new FetchComplaintExceptions(e.getMessage());
        }
    }
    private Double convertScoreToPoints(Double mark) {
        if (mark >= ParentService.GradeScores.EXCELLENT.i) {
            mark = (double) ParentService.GradeScores.EXCELLENT.j;
            return mark;
        }
        if (mark >= ParentService.GradeScores.GOOD.i) {
            mark = (double) ParentService.GradeScores.GOOD.j;
            return mark;
        }
        if (mark >= ParentService.GradeScores.AVERAGE.i) {
            mark = (double) ParentService.GradeScores.AVERAGE.j;
            return mark;
        }
        if (mark >= ParentService.GradeScores.BELOW_AVERAGE.i) {
            mark = (double) ParentService.GradeScores.BELOW_AVERAGE.j;
        } else {
            mark = (double) 0;
        }
        return mark;
    }
    public String getStudentMatriculationNumberForDatabase(Users studentUser) {
        Users student = userRepo2.findByEmail(studentUser.getEmail()); // Fetch the parent entity using the user's email or any other identifier
        if (student != null && student.getRole() == Roles.STUDENT) {
            return student.getMatricNumber(); // Return the matriculation number associated with the parent
        }
        return null; // Return null if student is not found or if the user is not a student
    }
    @Transactional(readOnly = true)
    private Users getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("No User not found with such email"));
    }
}
