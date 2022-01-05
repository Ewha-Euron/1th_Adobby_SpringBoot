package com.example.adobbyspringboot.service;

import com.example.adobbyspringboot.domain.diary.Diary;
import com.example.adobbyspringboot.domain.user.User;
import com.example.adobbyspringboot.payload.FlaskLineResponse;
import com.example.adobbyspringboot.payload.request.CorrectLineRequest;
import com.example.adobbyspringboot.payload.request.CorrectTextDiaryRequest;
import com.example.adobbyspringboot.payload.request.CreateTextDiaryRequest;
import com.example.adobbyspringboot.payload.response.DiaryResponse;
import com.example.adobbyspringboot.payload.response.LineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final UserService userService;
    private final MongoTemplate mongoTemplate;

    public Diary textToDiary(int diaryId, Integer date, String text){
        return new Diary(diaryId, date, text);
    }

    @Transactional
    public Diary updateText(String androidId, Integer date, String text){
        Query query = new Query().addCriteria(Criteria.where("_id").is(androidId));
        Update update = new Update();
        int index;

        User user = userService.findUserByAndroidId(androidId);
        List<Diary> diaries = user.getDiaries();
        if(diaries == null || diaries.isEmpty()){
            index = 1;
        }
        else{
            index = diaries.get(diaries.size() - 1).getDiaryId() + 1;
        }
        List<Diary> diaryList = new ArrayList<>();
        Diary diary = textToDiary(index, date, text);
        diaryList.add(diary);

        update.push("diaries").each(diaryList);
        mongoTemplate.updateFirst(query, update, "user");

        return diary;
    }

    public String textToLine(int diaryId, String androidId, String text){
        String url = "http://127.0.0.1:5000/text/line";
        Map<String, Object> params = new HashMap<>();
        params.put("androidId", androidId);
        params.put("diaryId", diaryId);

        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<FlaskLineResponse> response = restTemplate.getForEntity(
                    url + "/{androidId}/{diaryId}", FlaskLineResponse.class, params);
            if(response.getBody().getMessage().equals("Sucess")){
                return response.getBody().getLine();
            }
        }
        catch (Exception e){
            return "dbError";
        }
        return "serverError";
    }

    public LineResponse createTextDiary(CreateTextDiaryRequest diaryRequest){
        Diary diary = updateText(diaryRequest.getAndroidId(), diaryRequest.getDate(), diaryRequest.getText());
        String line = textToLine(diary.getDiaryId(), diaryRequest.getAndroidId(), diaryRequest.getText());
        return new LineResponse(diary.getDiaryId(), line);
    }

    public LineResponse patchTextDiary(CorrectTextDiaryRequest diaryRequest){
        Query query = new Query().addCriteria(
                Criteria.where("_id").is(diaryRequest.getAndroidId())
        );
        query.addCriteria(
                Criteria.where("diaries.diaryId").is(diaryRequest.getDiaryId())
        );
        Update update = new Update().set("diaries.$.text", diaryRequest.getText());
        mongoTemplate.updateFirst(query, update, "user");

        String line = textToLine(diaryRequest.getDiaryId(), diaryRequest.getAndroidId(), diaryRequest.getText());
        return new LineResponse(diaryRequest.getDiaryId(), line);
    }

    public void correctLine(CorrectLineRequest lineRequest){
        Query query = new Query().addCriteria(
                Criteria.where("_id").is(lineRequest.getAndroidId())
        );
        query.addCriteria(
                Criteria.where("diaries.diaryId").is(lineRequest.getDiaryId())
        );
        Update update = new Update().set("diaries.$.line", lineRequest.getLine());
        mongoTemplate.updateFirst(query, update, "user");
    }

    public DiaryResponse readOneDiary(String androidId, int diaryId){
        List<Diary> diaries = userService.findUserByAndroidId(androidId).getDiaries();
        Diary diary = diaries.get(diaryId - 1);
        return new DiaryResponse(diary.getText());
    }

    public void deleteDiary(String androidId, int diaryId){
        Query query = new Query().addCriteria(
                Criteria.where("_id").is(androidId)
        );
        query.addCriteria(
                Criteria.where("diaries.diaryId").is(diaryId)
        );
        Update update = new Update().set("diaries.$.isDeleted", true);
        mongoTemplate.updateFirst(query, update, "user");
    }
}