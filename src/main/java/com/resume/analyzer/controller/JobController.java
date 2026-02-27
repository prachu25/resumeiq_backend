package com.resume.analyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.resume.analyzer.model.Job;
import com.resume.analyzer.service.JobService;

@RestController
@RequestMapping("/api")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/jobs/create")
    public Job createJob(@RequestBody String content) {
        // @RequestBody converts raw request body into String.

        return jobService.createJob(content);
    }
}