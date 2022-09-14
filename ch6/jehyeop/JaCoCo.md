#JaCoCo
- Java Code Coverage
- JUnit 테스트를 통해 애플리케이션의 코드가 얼마나 테스트 됐는지 Line과 Branch를 기준으로 한 커버리리로 리포트 한다.
- 런타임으로 테스트케이스를 실행하고 커버리지를 체크하는 방식으로 동작

## JaCoCo 설정
1. plugins{ id 'jacoco' } 설정
2. jacoco { toolVersion = '0.8.7' } 버전 설정
   - 0.8.6 이하의 버전은 오류가 난다.
3. 출력할 파일 설정
4. 세부 규칙 설정

## 동작 확인
1. 프로그램을 동작시킨다.
2. build > reports.tests.test > index.html 파일을 웹으로 열기
   