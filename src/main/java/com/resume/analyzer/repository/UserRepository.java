package com.resume.analyzer.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.resume.analyzer.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // This interface extends MongoRepository.

    Optional<User> findByEmail(String email);
}
// This is the cutsom query method
// Spring Data automatically generates query based on method name.
