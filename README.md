1. 개발환경
    - Intellij
    - Java : 1.8
    - Spring boot 2.0.6.RELEASE
    - DB : h2 (in memory)
    - Maven3

2. 빌드 방법
    - mvn install
    - Intellij에서 Maven 빌드

3. 실행 방법
    - Intellij에서 KakaopayHwApplication 클래스 실행
    - java -jar kakaopay_hw-0.0.1-SNAPSHOT.jar

5. 개발 계획 (페이지, DB)
    - Web 어플리케이션으로 개발 : Java, Spring boot
    - Front-end 구현은 제약 없음 : 빠르게 개발 가능한 JSP 사용
    - 가능하면 in-memory DB 사용 : h2 사용, myBatis
    - 단위 테스트 : JUnit

6. JSP 페이지
    - 목록 조회 화면 (메인 화면)
        - '할일 목록' : TABLE로 현재 DB에 등록된 할일 목록 표시
        - '할일 등록' : 할일 등록 페이지로 이동
        - '할일 수정' : 선택된 할일의 수정하는 페이지로 이동
        - '할일 완료' : 선택된 할일들의 완료 처리
    - 할일 등록 화면
        - 할일 입력, 참조 할일 선택
    - 할일 수정 화면
        - 선택한 할일 수정, 참조 할일 수정

7. DB Table - schema.sql 파일
    - 할일 등록 TABLE
        ```
        CREATE TABLE WORK_LIST (
           ID int primary  key, -- 할일 ID
           TODO varchar(500),   -- 할일 내용
           CREATED_DATE  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- 할일 등록 날짜
           MODIFIED_DATE  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 최종 수정 시간
           COMPLETED_YN BOOLEAN default FALSE , -- 완료 여부
           COMPLETED_DATE TIMESTAMP             -- 완료 시간
         );```
     
    - 할일 참조 TABLE
        ```
        CREATE TABLE ID_REF (
             ID INT,
             REF_ID INT
           );
        ```

8. 테스트 : JUnit 사용
    - KakaopayHwApplicationTests : 서비스 클래스- WorkService 각 메소드 테스트
    - MapperTest : Mapper - WorkMapper 테스트
        - public void getNewId() 메소드의 경우 insertWorkTest() 메소드가 먼저 실행될 경우 newId는 17이 아닌 18
