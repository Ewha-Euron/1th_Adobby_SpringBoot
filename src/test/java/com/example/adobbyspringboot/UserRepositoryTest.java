package com.example.adobbyspringboot;

import com.example.adobbyspringboot.domain.diary.Diary;
import com.example.adobbyspringboot.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.ArrayList;
import java.util.List;

@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    public UserRepository userRepository;

    @Test
    public void userSave(){
        Diary diary = new Diary(1, 20220103, "hi");
        List<Diary> diaryList = new ArrayList<>();
        diaryList.add(diary);
        User user = new User("test", diaryList);

        userRepository.save(user);
    }

    @Test
    public void getDiaryList(){
        List<Diary> diaryList = userRepository.findById("test").get().getDiaries();
//        for(Diary diary : diaryList){
//            if(diary.isDeleted())
//                diaryList.remove(diary);
//        }
        System.out.println(diaryList);
    }
}
