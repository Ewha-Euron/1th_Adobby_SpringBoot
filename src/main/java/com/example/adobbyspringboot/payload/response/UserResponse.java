package com.example.adobbyspringboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long diaryId;
    private String line;
    //private Blob image;   이미지 요약 기능 시 사용
}