package com.educare.service;

import com.educare.dto.request.ExamRequest;
import com.educare.dto.response.ExamResponse;
import com.educare.exception.ResourceNotFoundException;
import com.educare.model.Exam;
import com.educare.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final SchoolClassService classService;

    public List<ExamResponse> getAllExams() {
        return examRepository.findAll().stream().map(ExamResponse::from).toList();
    }

    public ExamResponse getExamById(Long id) {
        return ExamResponse.from(findById(id));
    }

    public List<ExamResponse> getExamsByClass(Long classId) {
        return examRepository.findBySchoolClassClassId(classId)
            .stream().map(ExamResponse::from).toList();
    }

    public ExamResponse createExam(ExamRequest request) {
        Exam exam = Exam.builder()
            .schoolClass(classService.findById(request.getClassId()))
            .scheduledAt(request.getScheduledAt())
            .subject(request.getSubject())
            .status(Exam.ExamStatus.SCHEDULED)
            .build();
        return ExamResponse.from(examRepository.save(exam));
    }

    public ExamResponse updateExam(Long id, ExamRequest request) {
        Exam exam = findById(id);
        if (request.getScheduledAt() != null) exam.setScheduledAt(request.getScheduledAt());
        if (request.getSubject() != null) exam.setSubject(request.getSubject());
        if (request.getClassId() != null) exam.setSchoolClass(classService.findById(request.getClassId()));
        return ExamResponse.from(examRepository.save(exam));
    }

    public ExamResponse updateStatus(Long id, Exam.ExamStatus status) {
        Exam exam = findById(id);
        exam.setStatus(status);
        return ExamResponse.from(examRepository.save(exam));
    }

    public Exam findById(Long id) {
        return examRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Exam", id));
    }
}
