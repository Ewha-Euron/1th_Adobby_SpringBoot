package com.example.adobbyspringboot.domain.diary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import java.sql.Blob;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Document(collection = "diary")
public class Diary {
    @Id
    private Long id;
    private LocalDate date;
    private String text;
    //private Blob image; List 형태로 구현, 프론트에서 이미지를 blob 형태로 줘야 함
    private String line;
}
