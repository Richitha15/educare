package com.educare.service;

import com.educare.dto.request.EnrollmentRequest;
import com.educare.dto.response.EnrollmentResponse;
import com.educare.exception.BadRequestException;
import com.educare.exception.ResourceNotFoundException;
import com.educare.model.EnrollmentRecord;
import com.educare.repository.EnrollmentRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRecordRepository enrollmentRepository;
    private final StudentService studentService;
    private final SchoolClassService classService;

    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentRepository.findAll().stream().map(EnrollmentResponse::from).toList();
    }

    public List<EnrollmentResponse> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentStudentId(studentId)
            .stream().map(EnrollmentResponse::from).toList();
    }

    public List<EnrollmentResponse> getEnrollmentsByClass(Long classId) {
        return enrollmentRepository.findBySchoolClassClassId(classId)
            .stream().map(EnrollmentResponse::from).toList();
    }

    public EnrollmentResponse createEnrollment(EnrollmentRequest request) {
        boolean alreadyEnrolled = enrollmentRepository
            .findByStudentStudentIdAndSchoolClassClassId(request.getStudentId(), request.getClassId())
            .map(e -> e.getStatus() == EnrollmentRecord.EnrollStatus.ENROLLED)
            .orElse(false);

        if (alreadyEnrolled) {
            throw new BadRequestException("Student is already enrolled in this class");
        }

        EnrollmentRecord record = EnrollmentRecord.builder()
            .student(studentService.findById(request.getStudentId()))
            .schoolClass(classService.findById(request.getClassId()))
            .status(EnrollmentRecord.EnrollStatus.ENROLLED)
            .build();

        return EnrollmentResponse.from(enrollmentRepository.save(record));
    }

    public EnrollmentResponse updateStatus(Long id, EnrollmentRecord.EnrollStatus status) {
        EnrollmentRecord record = enrollmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Enrollment", id));
        record.setStatus(status);
        return EnrollmentResponse.from(enrollmentRepository.save(record));
    }
}
