package com.example.adobbyspringboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private int diaryId;
    private String line;
    private int date;
    private String title;
    //private Blob image;   이미지 요약 기능 시 사용
}