package com.resume.analyzer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.analyzer.model.Analysis;
import com.resume.analyzer.model.Job;
import com.resume.analyzer.model.Resume;
import com.resume.analyzer.repository.AnalysisRepository;
import com.resume.analyzer.repository.JobRepository;
import com.resume.analyzer.repository.ResumeRepository;

@Service
public class AnalysisService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private AnalysisRepository analysisRepository;

    public Analysis analyze(String resumeId, String jobId) {

        Resume resume = resumeRepository.findById(resumeId).orElse(null);

        Job job = jobRepository.findById(jobId).orElse(null);

        if (resume == null || job == null) {
            return null;
        }

        String resumeText = resume.getExtractedText().toLowerCase();

        String[] requiredSkills = job.getRequiredSkills();

        // List to stored match and missing skill
        List<String> matched = new ArrayList<>();
        List<String> missing = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();

        // SKILL MATCHING
        for (String skill : requiredSkills) {
            if (resumeText.contains(skill.toLowerCase())) {
                matched.add(skill);
            } else {
                missing.add(skill);
            }
        }

        int totalSkills = requiredSkills.length;
        int matchedSkills = matched.size();

        int score = 0;
        if (totalSkills > 0) {
            score = (matchedSkills * 100) / totalSkills;
        }

        // SUGGESTIONS LOGIC

        // Missing skills suggestion
        if (!missing.isEmpty()) {
            suggestions.add("Add missing skills: " + String.join(", ", missing));
        }

        // Section checks inside the resume
        if (!resumeText.contains("summary") && !resumeText.contains("objective")) {
            suggestions.add("Add a professional Summary or Objective section.");
        }

        if (!resumeText.contains("education")) {
            suggestions.add("Add an Education section.");
        }

        if (!resumeText.contains("experience") && !resumeText.contains("work experience")) {
            suggestions.add("Add a Work Experience section.");
        }

        if (!resumeText.contains("project") && !resumeText.contains("projects")) {
            suggestions.add("Add a Projects section.");
        }

        if (!resumeText.contains("skills") && !resumeText.contains("technical skills")) {
            suggestions.add("Add a Technical Skills section.");
        }

        if (!resumeText.contains("certification")) {
            suggestions.add("Add Certifications section if applicable.");
        }

        if (!resumeText.contains("achievement")) {
            suggestions.add("Add Achievements section to highlight accomplishments.");
        }

        // Low score suggestion
        if (score < 40) {
            suggestions.add("Your resume is not well aligned with this job description. Improve skill alignment.");
        }

        // SAVE ANALYSIS
        Analysis analysis = new Analysis();
        analysis.setResumeId(resumeId);
        analysis.setJobId(jobId);
        analysis.setMatchedSkills(matched.toArray(new String[0]));
        analysis.setMissingSkills(missing.toArray(new String[0]));
        analysis.setSuggestions(suggestions.toArray(new String[0]));
        analysis.setScore(score);
        analysis.setCreatedAt(LocalDateTime.now());

        return analysisRepository.save(analysis);
    }

    public Map<String, Object> getDashboard(String userId) {

        // Fetch all resumes of the user
        List<Resume> resumes = resumeRepository.findByUserId(userId);

        List<Analysis> allAnalysis = new ArrayList<>();

        // For each resume, fetch its analysis
        for (Resume resume : resumes) {
            allAnalysis.addAll(analysisRepository.findByResumeId(resume.getId()));
        }

        int totalResumes = resumes.size();
        int totalJobsApplied = allAnalysis.size();

        int bestScore = 0;
        int latestScore = 0;
        int averageScore = 0;
        int improvementFromFirst = 0;

        if (!allAnalysis.isEmpty()) {

            // Sort by date descending (latest first)
            allAnalysis.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));

            latestScore = allAnalysis.get(0).getScore();
            bestScore = allAnalysis.stream().mapToInt(Analysis::getScore).max().orElse(0);
            averageScore = (int) allAnalysis.stream().mapToInt(Analysis::getScore).average().orElse(0);

            // First score (oldest)
            int firstScore = allAnalysis.get(allAnalysis.size() - 1).getScore();

            // Improvement
            improvementFromFirst = latestScore - firstScore;
        }

        String performanceLevel;
        if (averageScore >= 80) {
            performanceLevel = "Excellent";
        } else if (averageScore >= 65) {
            performanceLevel = "Good";
        } else if (averageScore >= 50) {
            performanceLevel = "Average";
        } else {
            performanceLevel = "Needs Improvement";
        }

        Map<String, Object> response = new HashMap<>();
        response.put("totalResumes", totalResumes);
        response.put("totalJobsApplied", totalJobsApplied);
        response.put("bestScore", bestScore);
        response.put("latestScore", latestScore);
        response.put("averageScore", averageScore);
        response.put("improvementFromFirst", improvementFromFirst);
        response.put("performanceLevel", performanceLevel);

        return response;
    }

    public List<Analysis> getAnalysisByResume(String resumeId) {
        return analysisRepository.findByResumeId(resumeId);
    }
}