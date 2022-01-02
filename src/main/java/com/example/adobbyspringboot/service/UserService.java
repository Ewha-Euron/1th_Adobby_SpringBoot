package com.example.adobbyspringboot.service;

import com.example.adobbyspringboot.domain.diary.Diary;
import com.example.adobbyspringboot.domain.user.User;
import com.example.adobbyspringboot.payload.response.UserListResponse;
import com.example.adobbyspringboot.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<Diary> getUserDiary(String androidId, Long lastDiaryId, int size){
        int lastIndex = 0;
        List<Diary> diaries = findUserByAndroidId(androidId).getDiaries();
        if(diaries == null || diaries.isEmpty()){
            Diary diary = new Diary(LocalDate.now(), "","");
            List<Diary> emptyDiary = new ArrayList<>();
            emptyDiary.add(diary);
            return emptyDiary;
        }
        if(diaries.size() < lastDiaryId.intValue() + size)
            lastIndex = diaries.size();
        else
            lastIndex = lastDiaryId.intValue() + size;
        return diaries.subList(lastDiaryId.intValue(), lastIndex);
    }

    public UserListResponse userDiaryToResponse(String androidId, Long lastDiaryId, int size){
        List<Diary> diaries = getUserDiary(androidId, lastDiaryId, size);
        List<UserResponse> userResponses = new ArrayList<>();

        for(Diary diary : diaries){
            UserResponse userResponse = new UserResponse(diaries.indexOf(diary) + 1, diary.getLine());
            userResponses.add(userResponse);
        }

        return new UserListResponse(userResponses);
    }
}
