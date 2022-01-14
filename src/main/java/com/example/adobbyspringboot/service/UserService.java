package com.example.adobbyspringboot.service;

import com.example.adobbyspringboot.domain.diary.Diary;
import com.example.adobbyspringboot.domain.user.User;
import com.example.adobbyspringboot.payload.response.UserListResponse;
import com.example.adobbyspringboot.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MongoTemplate mongoTemplate;

    public User findUserByAndroidId(String androidId){
        User user = mongoTemplate.findById(androidId, User.class);
        if(user == null){
            return mongoTemplate.insert(User.builder()
                    .androidId(androidId)
                    .build());
        }
        return user;
    }

    public List<Diary> getUserDiary(String androidId){
        User user = findUserByAndroidId(androidId);
        List<Diary> diaries = user.getDiaries();

        Collections.sort(diaries);
        return diaries;
    }

    public UserListResponse userDiaryToResponse(String androidId, int yearMonth){
        int start = yearMonth * 100;
        int end = yearMonth * 100 + 32;

        List<Diary> diaries = getUserDiary(androidId);
        List<UserResponse> userResponses = new ArrayList<>();

        for(Diary diary : diaries){
            if(!diary.isDeleted() && diary.getDate() >= start && diary.getDate() <= end){
                UserResponse userResponse = new UserResponse(diary.getDiaryId(), diary.getLine(), diary.getDate(), diary.getTitle());
                userResponses.add(userResponse);
            }
        }

        return new UserListResponse(userResponses);
    }
}
