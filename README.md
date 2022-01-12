# 1th_Adobby_SpringBoot

Abstractive summarization 기법을 활용한 하루 일기 요약 어플리케이션, 하루한줄의 스프링부트 서버입니다.

### 주요 라이브러리 버전

Gradle, SpringBoot 2.6.2, Java 11

### 사용 모델
[Pororo](https://github.com/kakaobrain/pororo)

-----------
### 프로젝트 코드 구조
├── README.md - 리드미 파일<br>
│<br>
├── src/main/ - 어플리케이션 폴더<br>
│ ├── java/com/example/adobbyspringboot<br>
│ │ ├── controller/ - HTTP 요청<br>
│ │ ├── domain/ - Entity<br>
│ │ ├── payload/ - 요청, 응답을 위한 DTO<br>
│ │ └── service/ - 비즈니스 로직<br>
<br>
│ │ └── AdobbySpringbootApplication.java<br>
<br>
│ ├── resources<br>
│ │ └──application.properties- 어플리케이션 설정<br>
<br>
├── build.gradle - 사용 라이브러리<br>
├── gradlew<br>
└── settings.gradle

---
### 데이터베이스 구조

![image](https://user-images.githubusercontent.com/71377968/149147557-ffa9b832-cfaf-4444-80ea-3b2d186e3270.png)

- NoSQL 데이터베이스인 MongoDB를 사용함
- user가 diary list를 포함한 embedded 방식을 사용함<br>

----
### 상세 기능

<details>
    <summary>일기 조회</summary>
    <div markdown="1">
        <ul>
            <li><b>GET /scroll</b></li>
            <ul>
                <li>사용자의 선택에 따라 월별로 일기 목록을 조회할 수 있음</li>
                <li>목록에 보이는 내용은 각 일기의 요약 문장임</li>
            </ul>
            <li><b>GET /diary</b></li>
            <ul>
                <li>사용자가 리스트에서 이미 작성된 일기 클릭 시 상세 내용을 조회할 수 있음</li>
                <li>이때, 상세 내용에는 일기내용, 일기 요약 문장이 포함됨</li>
            </ul>
        </ul>
    </div>
</details>

<details>
    <summary>일기 작성 및 수정</summary>
    <div markdown="1">
        <ul>
            <li><b>POST /text</b></li>
            <ul>
                <li>텍스트로 일기를 작성할 수 있음</li>
                <li>response로 요약일기를 반환함</li>
            </ul>
            <li><b>POST /textcorrection</b></li>
            <ul>
                <li>작성한 텍스트 형식의 일기를 수정할 수 있음</li>
            </ul>
            <li><b>(To Flask) GET /text/line</b></li>
            <ul>
                <li>일기 최초 저장 시 혹은 일기 수정 시 SpringBoot서버에서 Flask서버로 요청을 보내 요약 일기를 데이터베이스에 저장하고 response로 받아 옴</li>
                <li>해당 값을 /text의 response에 포함하여 응답 보냄</li>
            </ul>
            <li><b>POST /line</b></li>
            <ul>
                <li>Pororo 모델로 요약된 한줄 일기를 수정할 수 있음</li>
            </ul>
        </ul>
    </div>
</details>

<details>
    <summary>일기 삭제</summary>
    <div markdown="1">
        <ul>
            <li><b>DELETE /eraser</b></li>
            <ul>
                <li>작성한 일기를 삭제할 수 있음</li>
            </ul>
        </ul>
    </div>
</details>

