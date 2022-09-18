# SpringBoot 동작 방식

## 용어

-   **Servlet [서블릿]** : 클라이언트의 요청을 처리하고 결과를 반환하는 자바 웹 프로그래밍 기술

-   **Servlet Container [서블릿 컨테이너]** : 서블릿 인스턴스를 생성, 관리. 스프링 부트의 경우 톰캣이 WAS의 역할과 스블릿 컨테이너의 역할을 수행한다.

-   **Dispatcher Servlet [디스패처 서블릿]** : 스프링 내부의 서블릿

<br>

## 동작 방식

![스프링_부트_동작_구조_001](https://user-images.githubusercontent.com/77231274/190912457-361379e7-0892-4769-8664-20ac01019867.png)

> 1. Servlet Container가 스프링 Dispatcher Servlet에 클라이언트 요청을 전달.  
>    (Servlet Container 와 Dispatcher Servlet 사이에 여러 filter 들이 존재.)
> 2. 핸들러 매핑이 요청 URI에 매핑된 핸들러(≒Controller)를 탐색.
> 3. 핸들러 리턴.
> 4. 핸들러에 맞는 핸들러 어댑터 탐색.
> 5. 핸들러 호출.
> 6. 값 리턴.
> 7. 리턴 받은 값을 ModelAndView로 가공해 반환.
> 8. 뷰형식인 경우 뷰 이름 전달.
> 9. 뷰 리턴.
> 10. 모델 전달.
> 11. 모델을 담아 뷰 리턴.
> 12. 응답

위의 과정을 거쳐 클라이언트의 요청에 따라 응답함.

뷰가 없는 REST 형식을 적용할 경우 스프링 내부의 MessageConverter가 요청과 응답 Body를 알아서 변환.

이러한 복잡한 과정을 스프링에서 해주고있기 때문에 개발자는 비즈니스 로직에 집중할 수 있음.
