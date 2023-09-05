# TravelApp

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

### 테스트 컨벤션

- 메서드 이름은 영어로 짓되, @DisplayName 어노테이션을 통해 한글로 설명하여 테스트를 쉽게 파악할 수 있도록 합니다.
- given/when/then 주석을 통해 테스트 내용을 쉽게 파악할 수 있도록 합니다.
  - 이 상황이 주어졌을때(given), 이렇게 하면(when), 이 결과가 나와야 한다(then)

<details>
<summary><strong>모범 예시</strong></summary>

```java
@DisplayName("signUp()은 ")
@Nested
class SignUp {
    @DisplayName("회원가입을 할 수 있다.")
    @Test
    void _willSuccess() throws Exception {
        // given
        SignUpRequest requestDto = new SignUpRequest("user11", "password",
                "password", "테스트유저1");

        // when then
        mockMvc.perform(
                    post("/api/v1/signUp")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("username 은 ")
    @Nested
    class _username {
        @DisplayName("null이면 실패한다.")
        @Test
        void null_willFail() throws Exception {
            // given
            SignUpRequest requestDto = new SignUpRequest(null, "password",
                    "password", "테스트유저1");

            // when then
            mockMvc.perform(
                        post("/api/v1/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("아이디를 입력해주세요."));
            }
        }
}
```
</details>

### 기타 합의사항

- **팀 프로젝트 관련**
  - 데일리 스크럼: 평일 10:00~10:10에 모여서 각자의 진행상황 공유
  - 정규시간에는 Zoom, 끝난 후에는 Discord에서 상시 모각코 (필참 아님)
- **개발 관련**
  - Model 영역 public 메서드에 대해 테스트 코드 작성 필수
  - 목록을 가져올 때는 get 동사를 쓴다

## 🔧 프로젝트 설계

### 클래스 다이어그램
![클래스 다이어그램](https://i.imgur.com/tkXa21D.png)

### 콘솔 화면
#### 시작
```
[메뉴]
1: 여행기록, 2: 여정기록, 3: 여행조회, 4: 여정조회, 5: 종료

메뉴 번호를 입력해주세요
0
```

**잘못 입력 시**
```
잘못된 메뉴 번호입니다. 다시 입력해주세요
```

#### 여행 기록
```
여행 기록을 시작합니다.

여행 이름:
ㅇㅇㅇ
시작 날짜 (2023-01-01 형식으로 입력):
0000-00-00
종료 날짜 (2023-01-01 형식으로 입력):
0000-00-00

여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.

여행 이름:
ㅇㅇㅇ
시작 날짜 (2023-01-01 형식으로 입력):
0000-00-00
종료 날짜 (2023-01-01 형식으로 입력):
0000-00-00

여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.
y

여행 기록이 완료되었습니다.
```

**잘못 입력 시**
```
잘못된 여행 번호입니다. 다시 입력해주세요

빈 칸으로는 입력할 수 없습니다. 다시 입력해주세요

0000-00-00 날짜 형식에 맞지 않습니다. 다시 입력해주세요

0000-00-00 00:00 날짜와 시간 형식에 맞지 않습니다. 다시 입력해주세요

컴마(,)는 입력할 수 없습니다.
```

#### 여정 기록

```
여정을 추가 기록할 여행을 선택해주세요
1: 여행 이름1 (0000-00-00 ~ 0000-00-00)
2: 여행 이름2 (0000-00-00 ~ 0000-00-00)
...생략

여행 번호:
0

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
체크인 시간 (YYYY-mm-DD HH:MM 형식으로 입력):
0000-00-00 00-00
체크아웃 시간 (YYYY-mm-DD HH:MM0 형식으로 입력):
0000-00-00 00-00

여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.
n

[2번째 여정]
...생략생략

여정 기록을 멈추고 싶다면 Y(y)를 입력해주세요.
Y

여정 기록이 완료되었습니다.

...생략

Enter를 누르면 시작 메뉴로 돌아갑니다.
```

**잘못 입력 시**
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

**보류**
```
조회할 파일 형식을 선택해주세요 (json, csv 중 입력):
json
```

#### 여행 목록 조회

```
1: 여행 이름1 (0000-00-00 ~ 0000-00-00)
2: 여행 이름2 (0000-00-00 ~ 0000-00-00)
...생략

Enter를 누르면 시작 메뉴로 돌아갑니다.
```

#### 여행 조회

```
조회할 여행을 번호를 입력해주세요.
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

Enter를 누르면 시작 메뉴로 돌아갑니다.
```

#### 여정 목록 조회

```
여정id   출발지    도착지   출발시간        도착시간            체크인             체크아웃
1       출발지    도착지 0000-00-00 00:00 0000-00-00 00:00  0000-00-00 00:00  0000-00-00 00:00
2       출발지    도착지 0000-00-00 00:00 0000-00-00 00:00  0000-00-00 00:00  0000-00-00 00:00
...생략

Enter를 누르면 시작 메뉴로 돌아갑니다.
```

#### 여정 조회

```
조회할 여정을 번호를 입력해주세요.
1

출발: 출발지, 0000-00-00 00:00
도착: 도착지, 0000-00-00 00:00
체크인: 0000-00-00 00:00
체크아웃: 0000-00-00 00:00

Enter를 누르면 시작 메뉴로 돌아갑니다.
```

### 파일 구조

#### json
**trips.json**
```json
[
  {
    "id": 1,
    "name": "이름",
    "startAt": "0000-00-00",
    "endAt": "0000-00-00"
  },
  {
    "id": 2,
    "name": "이름2",
    "startAt": "0000-00-00",
    "endAt": "0000-00-00"
  }
]
```

**trip_0.json**
```json
{
  "id": 1,
  "name": "이름",
  "startAt": "0000-00-00",
  "endAt": "0000-00-00",
  "itineraries": [
    {
      "id": 1,
      "departure": "출발지",
      "destination": "도착지",
      "accommodation": "숙박업소명"	
    },
    {
      "id": 2,
      "departure": "출발지",
      "destination": "도착지",
      "accommodation": "숙박업소명"
    }
  ]
}
```

**trip_0_itinerary1.json**
```json
{
  "id": 1,
  "departure": "출발지",
  "destination": "도착지",
  "departureAt": "0000-00-00 00:00",
  "arriveAt": "0000-00-00 00:00",
  "accommodation": "숙박업소",
  "checkInAt": null,
  "checkOutAt": null
}
```

#### csv

**trips.csv**
```csv
id,name,startAt,endAt
1,이름,0000-00-00,0000-00-00
2,이름,0000-00-00,0000-00-00
3,이름,0000-00-00,0000-00-00
```

**trip_0.csv**
```csv
id,name,startAt,endAt,itinerary_id,departure,destination,accommodation,checkInAt
1,이름,0000-00-00,0000-00-00,1,출발지,도착지,숙박명,0000-00-00 00:00
1,이름,0000-00-00,0000-00-00,2,출발지,도착지,숙박명,0000-00-00 00:00
1,이름,0000-00-00,0000-00-00,3,출발지,도착지,숙박명,0000-00-00 00:00
```

**trip_0_itinerary1.csv**
```csv
id,departure,destination,departureAt,arriveAt,accommodation,checkInAt,checkOutAt
1,출발지,도착지,0000-00-00 00:00,0000-00-00 00:00,숙박명,0000-00-00 00:00,0000-00-00 00:00
```
