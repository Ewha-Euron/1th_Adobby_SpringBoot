package com.example.adobbyspringboot.domain.user;

import com.example.adobbyspringboot.domain.diary.Diary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@Document(collation = "user")
public class User {
    @Id
    private Long id;
    private String adid;
    private List<Diary> diaries;
}
