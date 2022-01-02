package com.example.adobbyspringboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserListResponse {
    private List<UserResponse> userResponses;
}
