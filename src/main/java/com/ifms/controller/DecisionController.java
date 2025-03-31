package com.ifms.controller;

import com.ifms.entity.Decision;
import com.ifms.service.DecisionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/decisions")
public class DecisionController {
    private final DecisionService decisionService;

    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    /**
     * API: HR submits a final hiring decision.
     */
    @PostMapping("/submit")
    public ResponseEntity<Decision> submitDecision(@RequestBody Map<String, Object> requestData) {
        Decision savedDecision = decisionService.submitDecision(requestData);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDecision);
    }

    /**
     * API: Fetch all hiring decisions (HR Review).
     */
    @GetMapping("/all")
    public ResponseEntity<List<Decision>> getAllDecisions() {
        List<Decision> decisionList = decisionService.getAllDecisions();
        return ResponseEntity.ok(decisionList);
    }

    /**
     * API: Fetch decision for a specific interview.
     */
    @GetMapping("/interview/{interviewId}")
    public ResponseEntity<Decision> getDecisionByInterview(@PathVariable Long interviewId) {
        Decision decision = decisionService.getDecisionByInterview(interviewId);
        return ResponseEntity.ok(decision);
    }
}
