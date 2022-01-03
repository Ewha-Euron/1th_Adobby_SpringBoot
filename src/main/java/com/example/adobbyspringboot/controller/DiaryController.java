package com.example.adobbyspringboot.controller;

import com.example.adobbyspringboot.payload.request.CorrectLineRequest;
import com.example.adobbyspringboot.payload.request.CorrectTextDiaryRequest;
import com.example.adobbyspringboot.payload.request.CreateTextDiaryRequest;
import com.example.adobbyspringboot.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/text")
    public ResponseEntity<?> createNewDiary(@RequestBody @Valid CreateTextDiaryRequest diaryRequest){
        return new ResponseEntity<>(diaryService.createTextDiary(diaryRequest), HttpStatus.OK);
    }

    @PostMapping("/textcorrection")
    public ResponseEntity<?> correctDiary(@RequestBody @Valid CorrectTextDiaryRequest diaryRequest){
        return new ResponseEntity<>(diaryService.patchTextDiary(diaryRequest), HttpStatus.OK);
    }

    @PostMapping("/line")
    public ResponseEntity<?> fixLine(@RequestBody @Valid CorrectLineRequest lineRequest){
        diaryService.correctLine(lineRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/diary")
    public ResponseEntity<?> readDiary(@RequestParam @Valid String androidId, @RequestParam @Valid int diaryId){
        return new ResponseEntity<>(diaryService.readDiary(androidId, diaryId), HttpStatus.OK);
    }

    @DeleteMapping("/eraser")
    public ResponseEntity<?> eraseDiary(@RequestParam @Valid String androidId, @RequestParam @Valid int diaryId){
        diaryService.deleteDiary(androidId, diaryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
