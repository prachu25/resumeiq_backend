package com.resume.analyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

// This class maps to users collection in MongoDB.
@Document(collection = "users")
public class User {

    @Id // Primary key field (MongoDB _id)
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;
    private String password;

    // Constructor : Required by Spring to create object from DB
    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}