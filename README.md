# NHN PAYCO 핀테크 부문_기술과제_김의진

##  실행 방법

1. 로컬 환경 Maven 설치후 프로젝트 최상위 디렉터리(README.md 파일 위치) 이동  
2. 터미널에서 아래 명령어 실행
```
mvn clean package
java -jar was.jar
```

---
## 스펙별 구현 여부 (총 8건중 8건 구현 완료)
1. **HTTP/1.1 의 Host 헤더 해석** ✅
   - nhn.com, payco.com, 그외 요청으로 구분
   - config.json 설정파일 기반 host 확장 고려
2. **아래 사항 설정 파일 관리**  ✅
    - 서버 포트
    - HTTP/1.1의 Host 별 HTTP_ROOT 디렉토리 동적 할당
    - Host별 [403,404,500] 오류의 출력 HTML 파일 이름 관리
3. **403, 404, 500 오류 처리**  ✅
    - 오류 발생 시 적절한 HTML 반환
    - 설정 파일에 적은 에러 페이지 파일 이름 사용
4. **보안 규칙 적용**  ✅
    - HTTP_ROOT 디렉터리 상위 디렉터리 접근 여부
    - .exe 확장자 요청시 403 반환
    - 확장자 보안 규칙 확장 고려
5. **logback 프레임워크 사용 로깅 처리** ✅
    - 하루 단위 로그 파일 분리
    - 로그 내용에 따른 적절한 로그 레벨 적용
    - 오류 발생 시, StackTrace 전체를 로그 파일에 기록
6. **간단한 WAS 구현** ✅
   - url 매핑 기능
   - 추후 확장 고려
7. **현재 시각 출력 SimpleServlet 구현체 작성**
   - WAS와 SimpleServlet Interface를 포함한 SimpleServlet 구현 객체 jar 분리
8. **앞선 구현 사항 검증 Junit4 테스트 케이스 작성** (0/1)

---