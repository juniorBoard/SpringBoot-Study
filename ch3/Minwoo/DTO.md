# DTO
DTO (Data Transfer Object) 는 다른 레이어 간의 데이터 교환에 활용된다.

따라서 DTO에는 별도의 로직을 포함시키지 않음.

```java
public class MyDTO {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MyDTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

> Lombok 의 Getter, Setter 어노테이션으로 getter, setter 메서드 생략 가능