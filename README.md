# TravelApp

## 📍 소개
**프로젝트 내용**: 여행 기록/조회하는 Java 콘솔 어플리케이션

**프로젝트 목적**: Java 문법, 파일 입출력, 예외처리, 클래스 설계 능력 향상

**프로젝트 기간**: 2023년 9월 4일(월)~9월 8일(금)

### 기술 스택
- **언어**: Java 17
- **개발 환경**: IntelliJ, Gradle
- **라이브러리**: JUnit5, lombok
- **DevOps**: GitHub Actions(CI)
- **협업** : Slack, Discord 

## ⚖️ 컨벤션

### GitHub Flow
> 참고: https://velog.io/@deepred/GitHub-협업이-처음인-분을-위한-Github-Flow

- 브랜치 전략으로 GitHub Flow를 사용합니다.
- 모든 팀원의 Approve를 받아야 main에 Merge 할 수 있습니다.

### 코딩 컨벤션

- 구글 코딩 컨벤션을 사용합니다.
- 구글 코딩 컨벤션(번역): https://newwisdom.tistory.com/96
- IntelliJ 플러그인 적용법: https://055055.tistory.com/97

### 커밋 메시지 컨벤션

- 커밋 제목은 `prefix: 커밋 메시지` 형태로 합니다.
  - prefix의 목록과 각각의 용도는 IntelliJ 플러그인에서 볼 수 있습니다.
  - IntelliJ 플러그인 적용법: https://blog.naver.com/rinjyu/222157082860
- 커밋 내용을 자세하게 적습니다. (커밋 제목과 내용만 보더라도 코드 내용이 파악 가능할 정도로)

### 기타 합의사항

- **협업 관련**
  - 데일리 스크럼: 평일 10:00~10:10에 모여서 각자의 진행상황 공유
  - 정규시간에는 Zoom, 끝난 후에는 Discord에서 상시 모각코 (필참 아님)
- **추가 코딩 컨벤션**
  - 목록을 가져올 때는 get 동사를 쓴다
  - class/enum/interface/record 정의 시 맨 윗줄에는 줄바꿈을 넣고, 맨 아래 줄은 넣지 않는다

# 🗺️ 기획안

## 패키지 및 클래스 구성도
![패키지 및 클래스 구성도](패키지%20및%20클래스%20구성도.drawio.png)

## 콘솔 화면
**시작**
```
[메뉴]
1: 여행기록, 2: 여정기록, 3: 여행조회, 4: 여정조회, 5: 종료

메뉴 번호를 입력해주세요
0
```

**시작** - 잘못 입력 시
```
잘못된 메뉴 번호입니다. 다시 입력해주세요
```

-----

**여행 기록**
```
여행 기록을 시작합니다.

여행 이름:
ㅇㅇㅇ
시작 날짜 (2023-01-01 형식으로 입력):
0000-00-00
종료 날짜 (2023-01-01 형식으로 입력):
0000-00-00

여행에 대한 여정 기록을 시작합니다.

[1번째 여정]
출발지:
ㅇㅇㅇ
도착지:
ㄴㄴㄴ
출발 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):
0000-00-00 00-00
도착 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):
0000-00-00 00-00
숙박지 (Enter로 생략 가능):
ㅁㅁㅁ
체크인 시간 (YYYY-mm-DD HH:MM 형식으로 입력):
0000-00-00 00-00
체크아웃 시간 (YYYY-mm-DD HH:MM 형식으로 입력):
0000-00-00 00-00

여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.
n

[2번째 여정]
...생략생략

여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.
Y

여행 및 여정 기록이 완료되었습니다.
```

**여행 기록** - 잘못 입력 시
```
빈 칸으로는 입력할 수 없습니다. 다시 입력해주세요

0000-00-00 날짜 형식에 맞지 않습니다. 다시 입력해주세요

0000-00-00 00:00 날짜와 시간 형식에 맞지 않습니다. 다시 입력해주세요

컴마(,)는 입력할 수 없습니다.
```

-----

**여정 기록**

```
여정을 추가 기록할 여행을 선택해주세요
1: 여행 이름1 (0000-00-00 ~ 0000-00-00)
2: 여행 이름2 (0000-00-00 ~ 0000-00-00)
...생략

여행 번호:
0

여행에 대한 여정 기록을 시작합니다.

[3번째 여정]
출발지:
ㅇㅇㅇ
도착지:
ㄴㄴㄴ
출발 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):
0000-00-00 00-00
도착 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):
0000-00-00 00-00
숙박지 (Enter로 생략 가능)
ㅁㅁㅁ
체크인 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):
0000-00-00 00-00
체크아웃 시간 (Enter로 생략 가능, YYYY-mm-DD HH:MM 형식으로 입력):
0000-00-00 00-00

여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.
n

[4번째 여정]
...생략생략

여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.
Y

여정 기록이 완료되었습니다.
```

**여정 기록** - 잘못 입력 시
```
잘못된 여행 번호입니다. 다시 입력해주세요

빈 칸으로는 입력할 수 없습니다. 다시 입력해주세요

0000-00-00 날짜 형식에 맞지 않습니다. 다시 입력해주세요

0000-00-00 00:00 날짜와 시간 형식에 맞지 않습니다. 다시 입력해주세요

'출발과 도착' 또는 '체크인과 체크아웃' 중 하나의 시간은 꼭 입력하셔야 합니다.
출발 시간 (Enter로 생략 가능, 2023-01-01 23:00 형식으로 입력):
...생략

컴마(,)는 입력할 수 없습니다.
```

-----

**여행 조회**

```
1: 여행 이름1 (0000-00-00 ~ 0000-00-00)
2: 여행 이름2 (0000-00-00 ~ 0000-00-00)

조회할 여행의 번호를 입력해주세요.
1

"여행 이름"
기간: 0000-00-00 ~ 0000-00-00

[1번째 여정]
출발: 출발지, 0000-00-00 00:00
도착: 도착지, 0000-00-00 00:00
체크인: 0000-00-00 00:00
체크아웃: 0000-00-00 00:00

[2번째 여정]
...생략
```

**여행 조회** - 잘못 입력 시
```
잘못된 여행 번호입니다. 다시 입력해주세요
```

-----

**여정 조회**

```
1: 여행 이름1 (0000-00-00 ~ 0000-00-00)
2: 여행 이름2 (0000-00-00 ~ 0000-00-00)

조회할 여행의 번호를 입력해주세요.
1

1: 출발지 ~ 도착지
2: 출발지 ~ 도착지

조회할 여정의 번호를 입력해주세요.
1

출발: 출발지, 0000-00-00 00:00
도착: 도착지, 0000-00-00 00:00
체크인: 0000-00-00 00:00
체크아웃: 0000-00-00 00:00
```

**여정 조회** - 잘못 입력 시
```
잘못된 여행 번호입니다. 다시 입력해주세요
잘못된 여정 번호입니다. 다시 입력해주세요
```
**프로그램 종료시**
```
프로그램을 종료합니다.
```

## 저장 구조

### 폴더 및 파일 구성

```
travel/
├── csv/
│   ├── trip/
│   │   ├── trip_1.csv
│   │   ├── trip_2.csv
│   │   ├── trip_3.csv
│   └── trips.csv
├── json/
│   ├── trip/
│   │   ├── trip_1.json
│   │   ├── trip_2.json
│   │   ├── trip_3.json
│   └── trips.json
└── sequence.json
```

### csv

**trips.csv**
```csv
id,name,startAt,endAt
1,여행1,2023-01-01,2023-01-02
2,여행2,2023-02-01,2013-02-02
3,여행3,2023-03-01,2013-03-02
```

**trip_0.csv**
```csv
id,departure,destination,departureAt,arriveAt,accommodation,checkInAt,checkOutAt
1,출발지,도착지,2013-01-01T11:11,2013-01-02T11:11,,,
2,출발지,도착지,,,숙박지,2013-01-02T11:11,2013-01-02T11:11
3,출발지,도착지,2013-01-01T11:11,2013-01-02T11:11,숙박지,2013-01-02T11:11,2013-01-02T11:11
```

### json
**trips.json**
```json
[ {
  "id" : 1,
  "name" : "여행1",
  "startAt" : "2023-01-01",
  "endAt" : "2023-01-02"
}, {
  "id" : 2,
  "name" : "여행2",
  "startAt" : "2023-02-01",
  "endAt" : "2023-02-02"
}, {
  "id" : 3,
  "name" : "여행3",
  "startAt" : "2023-03-01",
  "endAt" : "2023-03-02"
} ]
```

**trip_{id}.json**
```json
[ {
  "id" : 1,
  "departure" : "출발지",
  "destination" : "도착지",
  "departureAt" : "2013-01-01T11:11",
  "arriveAt" : "2013-01-02T11:11",
  "accommodation" : "",
  "checkInAt" : "",
  "checkOutAt" : ""
}, {
  "id" : 2,
  "departure" : "출발지",
  "destination" : "도착지",
  "departureAt" : "",
  "arriveAt" : "",
  "accommodation" : "숙박지",
  "checkInAt" : "2013-01-02T11:11",
  "checkOutAt" : "2013-01-02T11:11"
}, {
  "id" : 1,
  "departure" : "출발지",
  "destination" : "도착지",
  "departureAt" : "2013-01-01T11:11",
  "arriveAt" : "2013-01-02T11:11",
  "accommodation" : "숙박지",
  "checkInAt" : "2013-01-02T11:11",
  "checkOutAt" : "2013-01-02T11:11"
} ]
```
