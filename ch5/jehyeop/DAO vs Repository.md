# DAO (Data Access Object) vs Repository

- “**데이터베이스에 접근하기 위한 로직**”을 관리하기 위한 객체
- 스프링 데이터 JPA에서 DAO의 개념은 리포지토리가 대체

## **DAO vs Repository**

### **DAO 패턴**

1. 데이터 지속성의 추상화 이며 **테이블 중심인 기본 저장소에 더 가까운 것으로 간주된다.**
2. **데이터베이스 테이블과 일치**하므로 스토리지에서 데이터를 보다 쉽게 전송/검색할 수 있으므로 쿼리를 숨길 수 있다.

ex)

**User domain**

```java
public class User {
    private Long id;
    private String userName;
    private String firstName;
    private String email;

    // getters and setters
}
```

**UserDao**

```java
public interface UserDao {
    void create(User user);
    User read(Long id);
    void update(User user);
    void delete(String userName);
}
```

**UserDaoImpl**

```java
public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;
    
    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User read(long id) {
        return entityManager.find(User.class, id);
    }

    // ...
}
```

→ 기본 스토리지와 통신하기 위해 JPA EntityManager 인터페이스 를 사용

→ 하고 있습니다. 또한 `User` 도메인에 대한 데이터 액세스 메커니즘을 제공합니다.

---

### **Repository 패턴**

1. DAO에 비해 비즈니스 로직에 더 가까운 더 높은 수준에 있습니다.
2. Repository도 DAO와 유사하게 데이터를 처리하고 쿼리를 숨깁니다.
3. Repository는 **DAO를 사용하여** 데이터베이스에서 데이터를 가져오고 도메인 개체를 채울 수 있습니다.
4. 도메인 개체에서 데이터를 준비하고 지속성을 위해 **DAO를 사용하여** 스토리지 시스템으로 보낼 수 있습니다.

ex)

UserRepository interFace

```java
public interface UserRepository {
    User get(Long id);
    void add(User user);
    void update(User user);
    void remove(User user);
}
```

UserRepositoryImpl

```java
public class UserRepositoryImpl implements UserRepository {

    private UserDaoImpl userDaoImpl;
    
    @Override
    public User get(Long id) {
        User user = userDaoImpl.read(id);
        return user;
    }

    @Override
    public void add(User user) {
        userDaoImpl.create(user);
    }

    // ...
}
```

→ `UserDaoImpl`을 사용하여 데이터베이스에서 데이터를 검색/전송

→ Repository는 DAO(데이터 액세스 계층) 위의 또 다른 계층일 뿐이다.

**다중 DAO가 있는 Repository**

Tweet domain

```java
public class Tweet {
    private String email;
    private String tweetText;    
    private Date dateCreated;

    // getters and setters
}
```

TweetDao

- Tweet 을 가져오는 인터페이스

```java
public interface TweetDao {
    List<Tweet> fetchTweets(String email);    
}
```

TweetDaoImpl

```java
@Component
public class TweetDaoImpl implements TweetDao {
    @Override
    public List<Tweet> fetchTweets(String email) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        
        //call Twitter API and prepare Tweet object
        
        return tweets;
    }
}
```

User 도메인 강화

- *트윗* 객체 목록을 유지하기 위해 *User 클래스의 UserSocialMedia* 하위 클래스
- *UserSocialMedia* 클래스는 *User* 도메인의 속성도 포함하는 복잡한 도메인

```java
public class UserSocialMedia extends User {
    private List<Tweet> tweets;
    // getters and setters
}
```

UserRepositoryImpl

```java
@Component
public class UserRepositoryImpl implements UserRepository {
    private UserDaoImpl userDaoImpl;
    private TweetDaoImpl tweetDaoImpl;
    
    @Override
    public User get(Long id) {
        UserSocialMedia user = (UserSocialMedia) userDaoImpl.read(id);
        
        List<Tweet> tweets = tweetDaoImpl.fetchTweets(user.getEmail());
        user.setTweets(tweets);
        
        return user;
    }
}
```

→ *UserRepositoryImpl* 은 UserDaoImpl을 사용하여 사용자 데이터를 추출하고 *TweetDaoImpl* 을 사용하여 사용자의 트윗을 *추출*

→ **리포지토리는 다양한 소스의 데이터에 액세스하기 위해 DAO에 의존합니다** .

---

### **정리**

| DAO | Repository |
| --- | --- |
| 데이터에 액세스하기에 적합 | 비즈니스 사용 사례를 구현기에 적합 |
|  데이터 지속성의 추상화 | 개체 컬렉션의 추상화 |
| 스토리지 시스템에 더 가깝다. (Repository 보다 하위 수준 개념) | Domain 객체에 더 가깝다 (DAO 보다 높은 수준의 개념) |
| 데이터 매핑/접근 계층으로 작동하여 쿼리를 숨김 | 도메인과 데이터 액세스 레이어 사이의 레이어로
데이터를 대조하고 도메인 객체를 준비하는 복잡성을 숨김 |
| Repository 를 사용하여 구현할 수 없다. | 기본 저장소에 액세스하기 위해 DAO를 사용할 수 있다. |
|  | 도메인 중심 설계를 장려하여 비기술적인 팀 구성원도 데이터 구조를 쉽게 이해할 수 있다. |

참고

[https://www.delftstack.com/ko/howto/java/difference-between-repository-pattern-and-dao-in-java/](https://www.delftstack.com/ko/howto/java/difference-between-repository-pattern-and-dao-in-java/)

[https://www.baeldung.com/java-dao-vs-repository](https://www.baeldung.com/java-dao-vs-repository)
