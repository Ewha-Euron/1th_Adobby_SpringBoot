package com.example.adobbyspringboot.domain.diary;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiaryRepository extends MongoRepository<Diary, Long> {
}
