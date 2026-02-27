package com.resume.analyzer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.resume.analyzer.model.Analysis;
import com.resume.analyzer.service.AnalysisService;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping("/analysis/run")
    public Analysis runAnalysis(
            @RequestParam String resumeId,
            @RequestParam String jobId) {
        // @RequestParam reads value from query parameter.

        return analysisService.analyze(resumeId, jobId);
    }

    @GetMapping("/analysis/resume/{resumeId}")
    public List<Analysis> getAnalysisByResume(@PathVariable String resumeId) {
        // @PathVariable extracts resumeId from URL.
        // Example: /api/analysis/resume/abc123

        return analysisService.getAnalysisByResume(resumeId);

    }

    @GetMapping("/dashboard/{userId}")
    public Map<String, Object> getDashboard(@PathVariable String userId) {

        return analysisService.getDashboard(userId);
    }
}