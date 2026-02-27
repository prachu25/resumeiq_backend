package com.resume.analyzer.service;

import java.util.*;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.analyzer.model.Job;
import com.resume.analyzer.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Master Skill database
    private final List<String> masterSkills = List.of(

            // Programming Languages
            "java", "python", "c", "c++", "c#", "javascript", "typescript",
            "go", "golang", "rust", "kotlin", "swift", "php", "ruby",

            // Frontend
            "html", "css", "sass", "bootstrap", "tailwind",
            "react", "reactjs", "angular", "vue", "nextjs",
            "redux", "jquery",

            // Backend
            "spring", "spring boot", "spring mvc", "hibernate",
            "node", "nodejs", "express", "django", "flask",
            "laravel", "rest api", "microservices",

            // Databases
            "mysql", "postgresql", "mongodb", "oracle",
            "sql", "sqlite", "redis", "firebase",

            // DevOps
            "docker", "kubernetes", "jenkins",
            "github actions", "ci/cd", "nginx",

            // Cloud
            "aws", "azure", "gcp",
            "ec2", "s3", "lambda",

            // Testing
            "junit", "mockito", "selenium",
            "postman", "jest",

            // Data Science / AI
            "machine learning", "deep learning",
            "tensorflow", "pytorch",
            "pandas", "numpy", "scikit-learn",

            // Mobile
            "android", "react native", "flutter",

            // Tools
            "git", "github", "gitlab",
            "jira", "maven", "gradle",

            // Architecture / Concepts
            "oop", "data structures", "algorithms",
            "design patterns", "system design",

            // Soft Skills
            "communication", "teamwork",
            "problem solving", "leadership");

    // Create Job Entry
    public Job createJob(String content) {

        String processedContent = content.toLowerCase();

        // Use Set to prevent duplicates
        Set<String> detectedSkills = new HashSet<>();

        for (String skill : masterSkills) {

            // Build safe word-boundary regex
            String pattern = "\\b" + Pattern.quote(skill.toLowerCase()) + "\\b";

            Pattern regex = Pattern.compile(pattern);
            if (regex.matcher(processedContent).find()) {
                detectedSkills.add(skill);
            }
        }

        Job job = new Job();
        job.setContent(content);
        job.setRequiredSkills(detectedSkills.toArray(new String[0]));

        return jobRepository.save(job);
    }
}