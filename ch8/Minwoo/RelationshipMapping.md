# 연관관계 매핑

## 들어가기 전에

### 용어

+ PK (Primary Key) : 고윳값, 기본 키, 식별 자
+ FK (Foreign Key) : 외래 키, 다른 테이블 레코드의 PK값을 참조한 값

### ERD (Entity Relationship Diagram) 예시
![ERD](https://github.com/juniorBoard/SpringBoot-Study/blob/main/ch8/Minwoo/DB_Diagram.png)

## 연관관계 매핑 종류

+ OneToOne (1대1)
+ OneToMany (1대N : 일대다)
+ ManyToOne (N대1 : 다대일)
+ ManyToMany (N대N : 다대다)

연관관계는 어떤 도메인 시점에서 보는가에 따라 달라진다.

팀과 멤버 관계에서 팀의 시점에서는 하나의 팀에 여러 멤버가 올 수 있어 일대다 관계이고,
멤버 시점에서는 여러 멤버들이 한 팀에 소속될 수 있기 때문에 다대일 관계이다.

## 매핑 방향
+ 단방향
+ 양방향

단방향 매핑은 한 쪽의 도메인이 다른 한 쪽의 도메인을 참조하고 잇는 것이고
양방향 매핑은 두 도메인 서로 참조하고 있는 것이다.

### 주인

연관관계를 맺으면 주인이라는 개념이 적용된다. 일반적으로 FK키를 가지고 있는 도메인을 주인으로 보고,
주인은 FK에 접근하여 읽고 쓰기가 가능하나 상대 도메인은 읽기만 가능하다.

(단방향의 경우 읽기도 불가능하다)

## OneToOne 단방향 매핑

### _@OneToOne_
+ 일대일 단방향 매핑 시 어노테이션은 주인 도메인에 사용한다.

#### 속성
+ fetch : 연관관계가 있는 도메인 로딩 전략 설정 옵션.
  + EAGER : 즉시 로딩
  + LAZY : 지연 로딩
+ optional : null값을 넣을 수 있게할 것인지, 아닌지를 설정하는 옵션.
  + true : nullable
  + false : not null

### _@JoinColumn_
+ 매핑할 외래키 컬럼 이름을 설정한다.
+ 어노테이션을 붙여주지 않으면 엔티티를 매핑하는 중간 테이블이 생겨 관리 포인트가 늘어나 좋지 않다.

#### 속성
+ name : 매핑할 외래키의 컬럼명을 지정하는 옵션

## OneToOne 양방향 매핑

### mappedBy

mappedBy 설정은 주인이 누구인지 설정해주는 속성이다.
 
주인이 **아닌** 엔티티에 설정해준다.

mappedBy 설정을 해주지 않으면, 단방향 2개와 같다.

mappedBy에는 반대 방향의 필드 이름이 와야만 한다.

양방향 매핑 시 주인만 FK를 가지고 있음.

### 양방향 매핑 시 주의사항

#### _@ToString_ 와 _@ResponseBody_

> **순환참조** 를 주의하자.

_@ToString_ 어노테이션을 사용할 때 순환참조가 발생한다.

Member를 문자열로 표현하기 위해 그 안에 Profile의 toString 을 실행.

Profile안에 Member가 있기때문에 Member의 toString 실행.

Member안에 Profile이 있기때문에 Profile의 toString 실행….

이러한 무한 루프에 빠지게 되는데 이 무한 루프를 **순환참조**라고 한다.

따라서, 양방향 매핑 시 _@ToString_ 에서 필드를 제외 시켜줘야한다.
`@ToString.Exclude`

또한, _@RestController_ 의 경우 _@ResponseBody_ 로 객체를 반환해 줄 때 JSON 형태로 변환해준다.

이때도 마찬가지로 매핑된 필드를 제외하여 해결 할 수 있지만, **DTO를 활용**하는 방법을 추천한다.

#### 매핑된 필드 설정

예를 들어 member - profile 양방향 관계를 맺는다면, member의 profile도 설정해주고
profile의 member도 설정해주어야한다.

어느 한 곳이라도 해주지않으면 값을 가져올 수 없다. (null)

## ManyToOne 단방향 매핑

> 한 팀에는 많은 멤버가 들어갈 수 있다. 따라서 일대다 관계이다.
> 
> 많은 멤버가 한 팀에 속해있다. 다대일 관계이다.

다대일, 일대다 관계를 판단할 때 위와 같이 생각하면 좋다.

일대다, 다대일은 상대적인 개념으로 볼 수 있다.

> 나는 하나 너는 여럿.
> 
> 나는 여럿 너는 하나.

앞에 있는 Many 또는 One이 엔티티(클래스)라고 생각하고 뒤에 있는 Many 또는 One이 관계를 맺는 필드로 생각하면 된다.

## ManyToOne, OneToMany 양방향 매핑

팀에 속한 전체 멤버를 조회할 상황이 발생할 수 있다.

만약 다대일 단방향 매핑이라면, 팀을 조회해서 그 팀 아이디를 가지고 있는 멤버들을 조회해야한다.

```java
Team team = teamRepository.findOne(~);
List<Member> members = memberRepository.findByTeam(team.getId());
```

이런 상황에 일대다 다대일 양방향 매핑을 맺으면 유용하다.

### 주의사항

일대다필드의 컬랙션 타입는 초기화해줄 것

안해주면 `NullPointerException` 발생.

### _@OneToMany_

*@OneToMany* 의 기본 fetch 전략은 LAZY 로딩이다.

따라서, Member 리스트를 조회하지 않으면 team select 쿼리 하나만 날아가고, Member 리스트 조회시 각각 select문이 두 번 날아가게 된다.

fetch 전략을 EAGER로 설정하면, 기본적으로 join이 포함된 쿼리가 날아간다.

LAZY 로딩 시에 쿼리문이 자주 날아가는게 문제가 될 수도 있고, EAGER 로딩 시에 불필요한 join이 일어날 수 도 있다. 상황에 맞게 잘 판단하여 적용해야 겠다.

### 다대다 매핑

![mapping01.png](https://github.com/juniorBoard/SpringBoot-Study/blob/main/ch8/Minwoo/mapping01.png)

다대다 매핑의 경우 교차 엔티티라고 부르는 중간 테이블을 생성해서 다대다 관계를 일대다 관계로 나눠 관리한다.

![mapping02.png](https://github.com/juniorBoard/SpringBoot-Study/blob/main/ch8/Minwoo/mapping02.png)

![mapping03.png](https://github.com/juniorBoard/SpringBoot-Study/blob/main/ch8/Minwoo/mapping03.png)
