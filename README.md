# java-attendance-precourse

## 구현 기능 목록

### 입력 기능

- [x] 기능 선택 입력
- [x] 닉네임 입력 기능
- [x] 등교 시간 입력 기능
- [x] 출석 수정 닉네임 입력 기능
- [x] 출석 수정 날짜 입력 기능
- [x] 출석 수정 시간 입력 기능

### 핵심 기능

- [x] csv 파일을 읽어와 출석부를 만든다.
- [x] 출석 확인
- [x] 출석 수정
- [x] 크루별 출석 기록 확인
- [ ] 제적 위험자 확인
- [ ] 종료
- [ ] 프로그램은 사용자가 종료할 때까지 종료되지 않으며, 해당 기능을 수행한 후 초기 화면으로 돌아간다.
  사용자가 잘못된 값을 입력할 경우 "[ERROR]"로 시작하는 메시지와 함께 IllegalArgumentException을 발생시킨 후 애플리케이션은 종료되어야 한다.

### 출력 기능

- [x] 프로그램을 실행하면 오늘의 월과 일 요일을 출력한다.
- [x] 출석 후 출석 기록 확인
- [x] 출석 수정 후 수정 기록 확인
- [x] 출석을 확인할 크루에 대한 출석 기록 출력
    - [ ] 출석, 지각, 결석 횟수를 출력
    - [ ] 횟수에 따라서 어떤 대상인지 출력
- [ ] 제적 위험자 결과를 출력

### 예외 상황

- [x] 기능 선택 항목, 날짜 또는 시간을 잘못된 형식으로 입력한 경우
- [ ] 이미 출석을 하였는데 다시 출석 확인을 하는 경우
- [x] 등록되지 않은 닉네임을 입력한 경우
- [x] 주말 또는 공휴일에 출석을 확인하거나 수정하는 경우
- [ ] 미래 날짜로 출석을 수정하는 경우
- [ ] 등교 시간이 캠퍼스 운영 시간이 아닌 경우

## 프로그램 흐름도

1. 크루의 출석 정보 csv 파일을 읽어와 저장한다.
2. 오늘의 날짜를 출력하여 기능을 선택한다.
3. Q(종료) 기능을 선택할 때 까지 프로그램을 종료되지 않는다.
3. 잘못된 입력을 할 경우 프로그램을 종료한다.
4. 선택한 기능에 대해서 기능을 수행하고, 다시 초기 화면으로 돌아간다.
