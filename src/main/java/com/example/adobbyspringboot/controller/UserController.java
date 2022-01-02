package com.example.adobbyspringboot.controller;

import com.example.adobbyspringboot.payload.response.UserListResponse;
import com.example.adobbyspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/scroll")
    public ResponseEntity<?> getDiaries(@RequestParam @Valid String androidId, @RequestParam @Valid  int lastDiaryId, @RequestParam @Valid  int size){
        Long diaryId = Long.valueOf(lastDiaryId);
        UserListResponse userListResponse = userService.userDiaryToResponse(androidId, diaryId, size);
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }
}
