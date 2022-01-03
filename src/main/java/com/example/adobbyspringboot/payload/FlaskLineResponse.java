package com.example.adobbyspringboot.payload;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class FlaskLineResponse {
    @NotNull
    private String message;
    private String line;
}
