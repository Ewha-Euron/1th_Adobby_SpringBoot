package com.example.adobbyspringboot.service;

import com.example.adobbyspringboot.domain.diary.Diary;
import com.example.adobbyspringboot.domain.user.User;
import com.example.adobbyspringboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByAndroidId(String androidId){
        return userRepository.findByAndroidId(androidId).orElse(
                User.builder()
                .androidId(androidId)
                .build()
        );
    }

    public List<Diary> getUserDiary(String androidId){
        return findUserByAndroidId(androidId).getDiaries();
    }
}
