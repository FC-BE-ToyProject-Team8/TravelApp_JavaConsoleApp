# TravelApp

## 📍 소개
**프로젝트 내용**: 여행 기록/조회하는 Java 콘솔 어플리케이션

**프로젝트 목적**: Java 문법, 파일 입출력, 예외처리, 클래스 설계 능력 향상

**프로젝트 기간**: 2023년 9월 4일(월)~9월 8일(금)

### 기술 스택
- **언어**: Java 17
- **개발 환경**: IntelliJ, Gradle
- **라이브러리**: JUnit5, lombok, Jackson
- **CI**: GitHub Actions

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
본 프로젝트는 여행과 여정 정보를 받아 JSON파일과 CSV 파일에 저장하고, 조회할 수 있는 어플리케이션만들기 프로젝트입니다.

저희는 프로젝트 평가항목 별 기획을 이렇게 하였습니다.

| 구분            | 내용                                                                                                            |
|---------------|---------------------------------------------------------------------------------------------------------------|
| 화면설계          | 메인화면이 잘 구현 되었는가.                                                                                              |
|               | **>>> 메인은 사용자에게 메뉴를 보여주며 메뉴에 원하는 기능을 선택할 수 있게 하였습니다. <br/>1: 여행기록, 2: 여정기록, 3: 여행조회, 4: 여정조회, 5: 종료**|
|               | 각각의 서브화면이 잘 구현 되었는가.                                                                                          |
|               | **>>> 메뉴 1,2,3,4 당 서브 화면을 구성하였고, 2번 여정기록과 4번 여정 조회를 해당 여행을 먼저 기록하고 조회 해야하기 때문에 이에 맞는 화면까지 설정해주었습니다.**|
| MVC 설계        | Model 클래스 설계를 잘 하였는가|
|               | **>>> domain 폴더 안에 객체지향적으로 맞게 관련 도메인을 만들었습니다.**|
|               | View 클래스 설계를 잘 하였는가|
|               | **>>> view 폴더 안에 화면을 구성하는 ConsoleView.java 와 유효성 검사를 위한 inputView.java를 만들었습니다.**|
|               | Controller 클래스 설계를 잘 하였는가|
|               | **>>> Controller Class와 DTO Class를 만들어 주었습니다. DTO Class는 JDK16이상 부터 제공하는 record를 활용하여 보다 쉽게 객체를 이동시킬 수 있게 하였습니다.**|
|JSON, CSV 파일 생성| 여행 및 여정 데이터를 JSON파일로 잘 생성하였는가|
|               | **>>> travel 폴더 아래에 json 폴더 아래에 여행 정보들만 모아놓은 trips.json 여행과 여정 정보를 함께 저장한 trip_ID.json 을 만들어 두었습니다.**|
|               | 여행 및 여정 데이터를 CSV파일로 잘 생성하였는가|
|               | **>>> travel 폴더 아래 csv 폴더 아래에 여행 정보들만 모아놓은 trips.csv와 여행과 여정 정보를 함께 저장한 trip_ID.csv 을 만들어 두었습니다.**|
|               | 한 여행에 여러 여정정보가 JSON, CSV 파일에 각각 저장 되었는가.|
|               | **>>> json과 csv 각각 trip 폴더를 만들어 단일 여행과 여행 내에 있는 복수의 여정들을 묶어 하나의 파일로 저장하였습니다.**|
| 기능 확인         | 여러개의 여행 정보를 입력받아서 저장하는 기능이 구현되었는가|
|               | **>>> 하나의 여행정보와 함께 여러개의 여정 정보를 받고 여행 정보저장이 끝나면 바로 또 다른 여행 정보를 받을 수 있게 하였습니다.**|
|               | 한 여행에 여러 여정을 기록하는 기능이 구현 되었는가|
|               | **>>> 한 여행 내에서 복수의 여정을 기록할 수 있게 하였습니다.**|
|               | 출발지, 도착지, 출발시간, 도착시간을 조회하는 기능이 구현되었는가.|
|               | **>>> 하나의 여행 조회시 그리고 여정 조회시 여정의 출발지, 도착지, 출발시간, 도착시간을 볼 수 있게 하였습니다.**|
|               | 체크인, 체크아웃 시간을 조회하는 기능이 구현되었는가.|
|               | **>>> 하나의 여행 조회시 그리고 여정 조회시 여정의 체크인, 체크아웃 시간을 볼 수 있게 하였습니다.**|
|               | JSON파일과 CSV파일에서 각각 여행과 여정정보를 조회하는 기능이 구현되었는가|
|               | **>>> 조회시 사용자로 하여금 FileType을 고르게 하여  JSON파일과 CSV파일에서 각각 여행과 여정정보를 조회할 수 있게 하였습니다.**|
| 서비스 완성도       | MVC구조로 서비스가 잘 표현이 되어서 구동이 되었는가.|
|               | **>>> MVC구조를 지키며 도메인 주도 설계(DDD)를 하였습니다.**|
|               | 출발지, 도착지, 출발시간, 도착시간을 조회하였을 때 조회 결과가 잘 출력이 되었는가.|
|               | **>>> 여정 조회를 하였을 때 한 여행에 상응하는 여정들 정보(출발지, 도착지, 출발시간, 도착시간)를 잘 출력하게 하였습니다.**|
|               | 체크인, 체크아웃 시간을 조회하였을 때 결과가 잘 출력이 되었는가.|
|               | **>>> 여정 조회를 하였을 때 한 여행에 상응하는 여정들 정보(체크인, 체크아웃 시간)를 잘 출력하게 하였습니다.**|
|               | 메인메뉴에서 서브 메뉴로 이동이 자연 스러우며 사용자의 편리성 있게 구현이 되었는가.|
|               | **>>> 조회시 사용자가 찾고싶은 FileType에서 찾게 하였으며 사용자가 종료하지 않은 이상 계속 프로그램 서비스를 이용할 수 있고, 서비스마다 연결이 잘 되게 작성하였습니다.**|

저희 프로젝트의 상세한 클래스 설계와 입출력 화면 설계, 폴더 및 파일 구성을 아래와 같습니다.
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
[1번째 여행]

여행 기록을 시작합니다.

여행 이름:
ㅇㅇㅇ
시작 날짜 입력 (YYYY-MM-DD 형식으로 입력):
0000-00-00
종료 날짜 입력 (YYYY-MM-DD 형식으로 입력):
0000-00-00

여행에 대한 여정 기록을 시작합니다.

[추가할 1번째 여정]
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
체크인 날짜와 시간 입력 (Enter로 생략 가능, YYYY-MM-DD HH:MM 형식으로 입력):
0000-00-00 00-00
체크아웃 날짜와 시간 입력 (Enter로 생략 가능, YYYY-MM-DD HH:MM 형식으로 입력):
0000-00-00 00-00

여정 기록을 계속하시겠습니까? (Y/N)
y

[추가할 2번째 여정]
...생략

여정 기록을 계속하시겠습니까? (Y/N)
N

여행 기록을 계속 하시겠습니까? (Y/N)
y

[2번째 여행]
...생략

여행 기록을 계속 하시겠습니까? (Y/N)
N

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
여정을 추가 기록할 여행을 먼저 선택해야 합니다.

1: 여행 이름1 (0000-00-00 ~ 0000-00-00)
2: 여행 이름2 (0000-00-00 ~ 0000-00-00)
...생략

해당 여행의 번호를 입력해주세요.
0

여행에 대한 여정 기록을 시작합니다.

[추가할 1번째 여정]
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

여정 기록을 계속하시겠습니까? (Y/N)
Y

[추가할 2번째 여정]
...생략생략

여정 기록을 계속하시겠습니까? (Y/N)
N

여정 기록이 완료되었습니다.
```

**여정 기록** - 잘못 입력 시
```
잘못된 여행 번호입니다. 다시 입력해주세요

빈 칸으로는 입력할 수 없습니다. 다시 입력해주세요

0000-00-00 날짜 형식에 맞지 않습니다. 다시 입력해주세요

0000-00-00 00:00 날짜와 시간 형식에 맞지 않습니다. 다시 입력해주세요

'출발과 도착' 또는 '체크인과 체크아웃' 중 하나의 시간은 꼭 입력하셔야 합니다.
출발 날짜와 시간 입력 (Enter로 생략 가능, YYYY-MM-DD HH:MM 형식으로 입력):
...생략

컴마(,)는 입력할 수 없습니다.
```

-----

**여행 조회**

```
여행 조회를 시작합니다.
조회할 파일 형식을 선택해주세요. (1. CSV / 2. JSON)
1
1: 여행 이름1 (0000-00-00 ~ 0000-00-00)
2: 여행 이름2 (0000-00-00 ~ 0000-00-00)
해당 여행의 번호를 입력해주세요.
1

"여행 이름"
기간: 0000-00-00 ~ 0000-00-00

[1번째 여정]
출발 : ㅇㅇㅇ
도착 : ㄴㄴㄴ
출발일 : 0000-00-00
도착일 : 0000-00-00
숙박 시설 : ㅇㅇ
체크인 : 0000-00-00
체크아웃 : 0000-00-00

[2번째 여정]
...생략
```

**여행 조회** - 잘못 입력 시
```
등록된 여행이 없습니다. 여행을 먼저 등록해주세요.
잘못된 여행 번호입니다. 다시 입력해주세요
```

-----

**여정 조회**

```
여정 조회를 시작합니다.
여정을 조회하기 위해서 먼저 해당 여행을 조회하겠습니다.
조회할 파일 형식을 선택해주세요. (1. CSV / 2. JSON)
1
여행 목록
1: 여행 이름1 (0000-00-00 ~ 0000-00-00)
2: 여행 이름2 (0000-00-00 ~ 0000-00-00)
해당 여행의 번호를 입력해주세요.
1
여정 목록
1: 출발지 -> 도착지
2: 출발지 -> 도착지

조회할 여정의 번호를 입력해주세요.
1

출발 : ㅇㅇㅇ
도착 : ㄴㄴㄴ
출발일 : 0000-00-00
도착일 : 0000-00-00
숙박 시설 : ㅇㅇ
체크인 : 0000-00-00
체크아웃 : 0000-00-00
```

**여정 조회** - 잘못 입력 시
```
잘못된 여행 번호입니다. 다시 입력해주세요
잘못된 여정 번호입니다. 다시 입력해주세요
등록된 여행이 없습니다. 여행을 먼저 등록해주세요.
```

**프로그램 종료 시**
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
