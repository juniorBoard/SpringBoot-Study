# TDD (Test-Driven-Development)

## TDD 기대 효과
1. 코드가 잘못되었을 때 잘못된 위치를 파악하기 쉽다. 
2. 리팩토링 시 테스트 코드를 실행하여 검증하면 되기 때문에 부담이 적고 **코드 신뢰도가 높아진다**.

>애플리케이션 코드를 먼저 작성하게 되면 경험상 테스트 코드 작성에 소홀해진다.  
TDD 방식을  적용하면 애플리케이션 코드를 먼저 작성하는 것보다 테스트 케이스 수가 늘어나게 된다.

<br>

## TDD 사이클
![TDD](https://user-images.githubusercontent.com/77231274/188546610-a94e8afd-3bc3-4238-a633-695ab91d864c.png)

1. 테스트 코드 작성.  
테스트 코드를 먼저 작성하기 때문에 컴파일 에러가 발생하는 것이 정상.
2. 테스트 코드가 통과하도록 애플리케이션 코드 작성.
3. 테스트 코드 통과 시 리팩토링.
4. 테스트 코드 실행.
5. 반복...

## TDD 예시
### 1. 테스트 코드 작성.

![TDD_EX_Square_01](https://user-images.githubusercontent.com/77231274/188546028-cb6a6241-2b15-4997-a7c5-034d998ff15f.PNG)


### 2. 애플리케이션 코드 작성.
![TDD_EX_Square_02](https://user-images.githubusercontent.com/77231274/188546242-f317fad5-61d4-4f76-812e-34ed40ecf7c6.PNG)

### 3. 테스트 실행.
![TDD_EX_Square_03](https://user-images.githubusercontent.com/77231274/188546421-ae656f68-f311-49d6-9cbe-2ad6411a265b.PNG)

### 4. 리팩토링.
![TDD_EX_Square_04](https://user-images.githubusercontent.com/77231274/188546440-7514ff73-dec4-4704-b574-c2ba485093e4.PNG)

### 5. 테스트 실행... 반복...
![TDD_EX_Square_03](https://user-images.githubusercontent.com/77231274/188546526-a47c2b5d-c103-4c61-9ed4-f140c90fabf9.PNG)
