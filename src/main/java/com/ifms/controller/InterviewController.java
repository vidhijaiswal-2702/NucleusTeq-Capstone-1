package com.ifms.controller;

import com.ifms.dto.InterviewDTO;
import com.ifms.model.Interview;
import com.ifms.model.InterviewStatus;
import com.ifms.model.User;
import com.ifms.repository.InterviewRepository;
import com.ifms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleInterview(@RequestBody InterviewDTO interviewDTO) {
        Optional<User> candidate = userRepository.findById(interviewDTO.getCandidateId());
        Optional<User> interviewer = userRepository.findById(interviewDTO.getInterviewerId());

        if (candidate.isEmpty() || interviewer.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid candidate or interviewer ID");
        }

        Interview interview = new Interview();
        interview.setCandidate(candidate.get());
        interview.setInterviewer(interviewer.get());
        interview.setScheduledTime(interviewDTO.getScheduledTime());
        interview.setStatus(InterviewStatus.SCHEDULED);

        interviewRepository.save(interview);
        return ResponseEntity.ok("Interview scheduled successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllInterviews() {
        return ResponseEntity.ok(interviewRepository.findAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInterviewStatus(@PathVariable Long id, @RequestParam InterviewStatus status) {
        Optional<Interview> interview = interviewRepository.findById(id);

        if (interview.isEmpty()) {
            return ResponseEntity.badRequest().body("Interview not found");
        }

        interview.get().setStatus(status);
        interviewRepository.save(interview.get());
        return ResponseEntity.ok("Interview status updated successfully");
    }
}

