package com.example.adobbyspringboot.domain.user;

import com.example.adobbyspringboot.domain.diary.Diary;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PUBLIC)
@Document(collection = "user")
public class User {
    @Id
    private String androidId;
    private List<Diary> diaries;
}
