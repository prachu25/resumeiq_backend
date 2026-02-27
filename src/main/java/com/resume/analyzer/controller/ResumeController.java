package com.resume.analyzer.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.resume.analyzer.model.Resume;
import com.resume.analyzer.service.ResumeService;

@RestController
// @RestController tells Spring that this class handles REST APIs.

@RequestMapping("/api") // BASE URL
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/resume/upload")
    public Resume uploadResume(
            @RequestParam("userId") String userId,
            @RequestParam("file") MultipartFile file) throws IOException {
        // MultipartFile is used to receive uploaded file.

        return resumeService.uploadResume(userId, file);
    }
}