package com.educare.service;

import com.educare.dto.request.GradeChangeRequest;
import com.educare.dto.response.GradeChangeResponse;
import com.educare.exception.ResourceNotFoundException;
import com.educare.model.GradeChange;
import com.educare.model.User;
import com.educare.repository.GradeChangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeChangeService {

    private final GradeChangeRepository gradeChangeRepository;
    private final GradeService gradeService;

    public List<GradeChangeResponse> getChangesByGrade(Long gradeId) {
        return gradeChangeRepository.findByGradeGradeId(gradeId)
            .stream().map(GradeChangeResponse::from).toList();
    }

    public List<GradeChangeResponse> getPendingChanges() {
        return gradeChangeRepository.findByStatus(GradeChange.ChangeStatus.PENDING)
            .stream().map(GradeChangeResponse::from).toList();
    }

    public GradeChangeResponse requestChange(GradeChangeRequest request, User requestedBy) {
        GradeChange change = GradeChange.builder()
            .grade(gradeService.findById(request.getGradeId()))
            .changedBy(requestedBy)
            .reason(request.getReason())
            .evidenceUri(request.getEvidenceUri())
            .status(GradeChange.ChangeStatus.PENDING)
            .build();
        return GradeChangeResponse.from(gradeChangeRepository.save(change));
    }

    public GradeChangeResponse updateChangeStatus(Long id, GradeChange.ChangeStatus status) {
        GradeChange change = gradeChangeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Grade change request", id));
        change.setStatus(status);
        return GradeChangeResponse.from(gradeChangeRepository.save(change));
    }
}
