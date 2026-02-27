package com.resume.analyzer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.resume.analyzer.model.Analysis;

@Repository
public interface AnalysisRepository extends MongoRepository<Analysis, String> {

    List<Analysis> findByResumeId(String resumeId);
}