package com.example.adobbyspringboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LineResponse {
    private int diaryId;
    private String line;
}
