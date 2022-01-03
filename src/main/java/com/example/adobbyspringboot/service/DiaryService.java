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
        Diary diary = textToDiary(index, date, text);

        update.push("diaries").each(diary);
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
        String line = textToLine(diaryRequest.getDiaryId(), diaryRequest.getAndroidId(), diaryRequest.getText());
        return new LineResponse(diaryRequest.getDiaryId(), line);
    }

    public void correctLine(CorrectLineRequest lineRequest){
        Update update = new Update().set("line", lineRequest.getLine());
        User user = mongoTemplate.update(User.class)
                .matching(new Query(new Criteria().andOperator(
                        Criteria.where("_id").is(lineRequest.getAndroidId()),
                        Criteria.where("diaries").elemMatch(Criteria.where("diaryId").is(lineRequest.getDiaryId())))))
                .apply(update)
                .findAndModifyValue();
    }

    public DiaryResponse readDiary(String androidId, int diaryId){
        Diary diary = mongoTemplate.findOne(new Query(new Criteria().andOperator(
                Criteria.where("_id").is(androidId),
                Criteria.where("diaries").elemMatch(Criteria.where("diaryId").is(diaryId)))), Diary.class);
        System.out.println(diary);
        return new DiaryResponse(diary.getText());
    }

    public void deleteDiary(String androidId, int diaryId){
        Update update = new Update().set("isDeleted", true);
        Diary diary = mongoTemplate.update(Diary.class)
                .matching(new Query(new Criteria().andOperator(
                        Criteria.where("_id").is(androidId),
                        Criteria.where("diaries").elemMatch(Criteria.where("diaryId").is(diaryId)))))
                .apply(update)
                .findAndModifyValue();
    }
}