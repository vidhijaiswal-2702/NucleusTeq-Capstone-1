//package com.ifms.controller;
//
//import com.ifms.entity.*;
//import com.ifms.repository.CandidateEvaluationRepository;
//import com.ifms.repository.EvaluationMetricRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/feedback")
//public class InterviewFeedbackController {
//
//    @Autowired
//    private CandidateEvaluationRepository candidateEvaluationRepository;
//
//    @Autowired
//    private EvaluationMetricRepository evaluationMetricRepository;
//
//    // Submit L1 or L2 Evaluation
//    @PostMapping("/submit")
//    public String submitEvaluation(@RequestBody CandidateEvaluation candidateEvaluation) {
//        candidateEvaluationRepository.save(candidateEvaluation);
//        return "Evaluation Submitted Successfully";
//    }
//
//    // Get evaluations for HR review
//    @GetMapping("/evaluations")
//    public List<CandidateEvaluation> getAllEvaluations() {
//        return candidateEvaluationRepository.findAll();
//    }
//}
