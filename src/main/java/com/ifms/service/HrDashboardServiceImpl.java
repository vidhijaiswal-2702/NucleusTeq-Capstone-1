package com.ifms.service;

import com.ifms.dto.AssignInterviewDTO;
import com.ifms.dto.FeedbackReviewDTO;
import com.ifms.dto.InterviewDecisionDTO;
import com.ifms.dto.InterviewSummaryDTO;
import com.ifms.dto.ScheduleRoundDTO;
import com.ifms.entity.Feedback;
import com.ifms.entity.Interview;
import com.ifms.entity.InterviewStatus;
import com.ifms.repository.FeedbackRepository;
import com.ifms.repository.InterviewRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.ifms.exception.ResourceNotFoundException;  // ✅ Import this


import java.util.List;
import java.util.stream.Collectors;

@Service
public class HrDashboardServiceImpl implements HrDashboardService {

    private final InterviewRepository interviewRepository;
    private final FeedbackRepository feedbackRepository;

    public HrDashboardServiceImpl(InterviewRepository interviewRepository, FeedbackRepository feedbackRepository) {
        this.interviewRepository = interviewRepository;
        this.feedbackRepository = feedbackRepository;
    }
    
    @Override
    public void assignInterview(AssignInterviewDTO dto) {
        Interview interview = new Interview();
        interview.setCandidateName(dto.getCandidateName());
        interview.setCandidateEmail(dto.getCandidateEmail());
        interview.setInterviewerName(dto.getInterviewerName());
        interview.setPosition(dto.getPosition());
        interview.setInterviewDate(dto.getInterviewDate());
        interview.setStatus(InterviewStatus.SCHEDULED);

        interviewRepository.save(interview);
    }

    @Override
    public List<InterviewSummaryDTO> getAssignedInterviews() {
        return interviewRepository.findAll().stream()
                .map(interview -> new InterviewSummaryDTO(
                        interview.getCandidateName(),
                        interview.getCandidateEmail(),
                        interview.getInterviewerName(),
                        interview.getPosition(),
                        interview.getInterviewDate(),
                        interview.getStatus().name()))
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackReviewDTO getFeedbackForReview(Long interviewId) {
    	Feedback feedback = feedbackRepository.findByInterviewId(interviewId)
    		    .orElseThrow(() -> new ResourceNotFoundException("Feedback not found for interview ID: " + interviewId));
        return new FeedbackReviewDTO(
                feedback.getFeedbackLevel().name(),
                feedback.getDecisionLevel().name(),
                feedback.getOverallComments());
    }

    @Override
    @Transactional
    public String makeFinalDecision(InterviewDecisionDTO decisionDTO) {
        Interview interview = interviewRepository.findById(decisionDTO.getInterviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found!"));

        interview.setStatus(InterviewStatus.FINALIZED);  // ✅ Ensure status is set correctly
        interview.setHrComments(decisionDTO.getRemarks());  // ✅ Store remarks in hrComments

        interviewRepository.save(interview);
        return "Final decision saved!";
    }
    
    @Override
    @Transactional
    public void scheduleFurtherRound(Long interviewId, ScheduleRoundDTO scheduleRoundDTO) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found with ID: " + interviewId));

        // Update interview details for further round scheduling
        interview.setInterviewDate(scheduleRoundDTO.getNewInterviewDate());
        interview.setInterviewerName(scheduleRoundDTO.getNewInterviewerName());
        interview.setStatus(InterviewStatus.SCHEDULED);  // Update status to scheduled

        interviewRepository.save(interview);
    }


    @Override
    @Transactional
    public void deleteInterview(Long interviewId) {
        if (!interviewRepository.existsById(interviewId)) {
            throw new ResourceNotFoundException("Interview not found with ID: " + interviewId);
        }
        
        interviewRepository.deleteById(interviewId);
    }

}
