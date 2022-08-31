# MyBatis 사용

## MyBatis 3.0 이전

[참고](https://github.com/myoungji-kim/KakaoTogether)

## MyBatis 3.0 이후

### 0. 설정

 + application.properties

```properties
# (DB 설정 생략...)
mybatis.mapper-locations=mybatis/mapper/**/*.xml
mybatis.type-aliases-package=com.example.mybatisguide.model
```
mybatis.mapper-locations : 쿼리문을 작성하는 xml 파일 위치 설정.

mybatis.type-aliases-package : 이 설정을 통해 쿼리문의 parameterType, resultType 작성시 패키지명을 생략가능하게 함. 

<br>

### 1. DAO인터페이스와 Mapper

+ DAO인터페이스 

_java/com/example/mybatisquide/dao/CompanyDAO.java_
```java
@Mapper
public interface CompanyDAO {
    int insertCompany(@Param("company") Company company);
    Company selectCompanyByName(String name);
}
```
메서드명과 xml파일 내 쿼리문 id와 일치해야 매칭됨.

<br>

+ Mapper.xml 

_resources/mybatis/mapper/Company.xml_
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.mybatisguide.dao.CompanyDAO">
    <insert id="insertCompany" parameterType="Company">
        INSERT INTO company (
            name,
            ceo
        ) VALUES (
            #{company.name},
            #{company.ceo}
        )
    </insert>
    <select id="selectCompanyByName" resultType="Company">
        SELECT * FROM company WHERE name = #{value}
    </select>
</mapper>
```
필요에 따라 parameterType, resultType 등을 설정.

'#{}'를 사용하여 받아온 인자를 변수로 사용.

<br>

### 2. DAO와 Mapper 통합

+ Mapper

_java/com/example/mybatisguide/mapper/MemberMapper.java_
```java
@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO member(name, age) VALUES(#{member.name}, #{member.age})")
    int insert(@Param("member") Member member);

    @Select("SELECT * FROM member")
    List<Member> findAll();

    @Select("SELECT * FROM member WHERE id = #{id}")
    Member findOneById(Long id);
}
```

@Insert, @Select 어노테이션에 쿼리문을 작성.

'#{}'를 사용하여 받아온 인자를 변수로 사용.