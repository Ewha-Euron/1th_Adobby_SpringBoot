package com.example.adobbyspringboot.domain.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByAndroidId(String androidId);
}
