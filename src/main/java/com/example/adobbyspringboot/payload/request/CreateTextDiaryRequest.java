package com.example.adobbyspringboot.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateTextDiaryRequest {
    @NotNull
    private String text;
    @NotNull
    private Integer date;
    @NotNull
    private String androidId;
}
