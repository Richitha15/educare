package com.educare.service;

import com.educare.dto.request.GradeRequest;
import com.educare.dto.response.GradeResponse;
import com.educare.exception.BadRequestException;
import com.educare.exception.ResourceNotFoundException;
import com.educare.model.Grade;
import com.educare.model.User;
import com.educare.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;
    private final ExamService examService;

    public List<GradeResponse> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentStudentId(studentId)
            .stream().map(GradeResponse::from).toList();
    }

    public List<GradeResponse> getGradesByExam(Long examId) {
        return gradeRepository.findByExamExamId(examId)
            .stream().map(GradeResponse::from).toList();
    }

    public GradeResponse getGradeById(Long id) {
        return GradeResponse.from(findById(id));
    }

    public GradeResponse submitGrade(GradeRequest request, User submittedBy) {
        boolean exists = gradeRepository
            .findByStudentStudentIdAndExamExamId(request.getStudentId(), request.getExamId())
            .isPresent();
        if (exists) throw new BadRequestException("Grade already submitted for this student and exam");

        Grade grade = Grade.builder()
            .student(studentService.findById(request.getStudentId()))
            .exam(examService.findById(request.getExamId()))
            .gradeValue(request.getGradeValue())
            .submittedBy(submittedBy)
            .status(Grade.GradeStatus.SUBMITTED)
            .build();
        return GradeResponse.from(gradeRepository.save(grade));
    }

    public GradeResponse updateGradeStatus(Long id, Grade.GradeStatus status) {
        Grade grade = findById(id);
        grade.setStatus(status);
        return GradeResponse.from(gradeRepository.save(grade));
    }

    public Double getStudentAverage(Long studentId) {
        return gradeRepository.findAverageGradeByStudentId(studentId);
    }

    public Grade findById(Long id) {
        return gradeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Grade", id));
    }
}
