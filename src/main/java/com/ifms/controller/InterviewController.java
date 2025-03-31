package com.ifms.controller;

import com.ifms.entity.Interview;
import com.ifms.entity.User;
import com.ifms.service.InterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    /**
     * API: HR schedules an interview.
     */
    @PostMapping("/schedule")
    public ResponseEntity<Interview> scheduleInterview(@RequestBody Interview interview) {
        Interview scheduledInterview = interviewService.scheduleInterview(interview);
        return ResponseEntity.ok(scheduledInterview);
    }

    /**
     * API: HR updates an interview.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Interview> updateInterview(@PathVariable Long id, @RequestBody Interview updatedInterview) {
        Interview interview = interviewService.updateInterview(id, updatedInterview);
        return ResponseEntity.ok(interview);
    }

    /**
     * API: HR deletes an interview.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * API: HR fetches all scheduled interviews.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Interview>> getAllInterviews() {
        List<Interview> interviews = interviewService.getAllInterviews();
        return ResponseEntity.ok(interviews);
    }

    /**
     * API: Interviewers fetch their assigned interviews.
     */
    @GetMapping("/assigned/{interviewerId}")
    public ResponseEntity<List<Interview>> getInterviewsByInterviewer(@PathVariable Long interviewerId) {
        User interviewer = new User();
        interviewer.setId(interviewerId);
        List<Interview> interviews = interviewService.getInterviewsByInterviewer(interviewer);
        return ResponseEntity.ok(interviews);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterviewById(@PathVariable Long id) {
        Interview interview = interviewService.getInterviewById(id);
        return ResponseEntity.ok(interview);
    }
    @GetMapping("/assigned")
    public ResponseEntity<List<Interview>> getAssignedInterviews(@RequestParam String email) {
        List<Interview> interviews = interviewService.getAssignedInterviewsByEmail(email);
        return ResponseEntity.ok(interviews);
    }

}
