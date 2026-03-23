package com.educare.service;

import com.educare.dto.request.AttendanceRequest;
import com.educare.dto.response.AttendanceResponse;
import com.educare.exception.ResourceNotFoundException;
import com.educare.model.AttendanceRecord;
import com.educare.model.User;
import com.educare.repository.AttendanceRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRepository;
    private final StudentService studentService;
    private final SchoolClassService classService;

    public List<AttendanceResponse> getByStudent(Long studentId) {
        return attendanceRepository.findByStudentStudentId(studentId)
            .stream().map(AttendanceResponse::from).toList();
    }

    public List<AttendanceResponse> getByClassAndDate(Long classId, LocalDate date) {
        return attendanceRepository.findBySchoolClassClassIdAndDate(classId, date)
            .stream().map(AttendanceResponse::from).toList();
    }

    public List<AttendanceResponse> getByStudentDateRange(Long studentId, LocalDate from, LocalDate to) {
        return attendanceRepository.findByStudentStudentIdAndDateBetween(studentId, from, to)
            .stream().map(AttendanceResponse::from).toList();
    }

    public AttendanceResponse markAttendance(AttendanceRequest request, User recordedBy) {
        AttendanceRecord record = AttendanceRecord.builder()
            .student(studentService.findById(request.getStudentId()))
            .schoolClass(classService.findById(request.getClassId()))
            .date(request.getDate())
            .status(request.getStatus())
            .recordedBy(recordedBy)
            .build();
        return AttendanceResponse.from(attendanceRepository.save(record));
    }

    public AttendanceResponse updateAttendance(Long id, AttendanceRecord.AttendanceStatus status) {
        AttendanceRecord record = attendanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Attendance record", id));
        record.setStatus(status);
        return AttendanceResponse.from(attendanceRepository.save(record));
    }

    public Map<String, Long> getAttendanceSummary(Long studentId) {
        long present = attendanceRepository.countPresentByStudentId(studentId);
        long absent  = attendanceRepository.countAbsentByStudentId(studentId);
        return Map.of("present", present, "absent", absent, "total", present + absent);
    }
}
