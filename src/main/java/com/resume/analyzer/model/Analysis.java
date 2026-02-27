package com.resume.analyzer.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "analysis")
public class Analysis {

    @Id
    private String id;

    private String resumeId;
    private String jobId;

    private int score;
    private String[] matchedSkills;
    private String[] missingSkills;
    private String[] suggestions;
    private LocalDateTime createdAt;

    public Analysis() {
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResumeId() {
        return resumeId;
    }

    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String[] getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(String[] matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public String[] getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(String[] missingSkills) {
        this.missingSkills = missingSkills;
    }

    public String[] getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String[] suggestions) {
        this.suggestions = suggestions;
    }

}