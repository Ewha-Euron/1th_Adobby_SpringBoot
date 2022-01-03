package com.example.adobbyspringboot.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CorrectLineRequest {
    @NotNull
    String androidId;
    @NotNull
    int diaryId;
    @NotNull
    String line;
}
