package com.example.adobbyspringboot.domain.diary;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

//import java.sql.Blob;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Document(collection = "diary")
public class Diary implements Comparable {
    private int diaryId;
    private Integer date;
    private String text;
    //private Blob image; List 형태로 구현, 프론트에서 이미지를 blob 형태로 줘야 함
    private String line;
    private boolean isDeleted;
    public Diary(int id, Integer today, String txt){
        diaryId = id;
        date = today;
        text = txt;
        isDeleted = false;
    }
    @Override
    public int compareTo(Object o){
        Diary d = (Diary) o;
        if(this.getDate() < ((Diary) o).getDate()){
            return 1;
        }
        else if(this.getDate() == ((Diary) o).getDate()){
            return 0;
        }
        else{
            return -1;
        }
    }
}
