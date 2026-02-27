package com.resume.analyzer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.resume.analyzer.model.Job;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {

}