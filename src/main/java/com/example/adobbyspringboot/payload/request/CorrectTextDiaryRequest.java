package com.example.adobbyspringboot.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CorrectTextDiaryRequest {
    @NotNull
    private int diaryId;
    @NotNull
    private String text;
    @NotNull
    private String androidId;
}
