## 스프링 부트 - HATEOAS(Hypermedia As The Engine Of Application State)
- 링크에 사용 가능한 URL을 리소스로 전달하여 client가 참고하여 사용할 수 있도록 하는 것
- HATEOAS는 RESTful API를 사용하는 클라이언트가 전적으로 서버에 의해 동적으로 상호작용을 할 수 있다.
  - 클라이언트가 서버에 요청시 서버는 요청에 의존되는 URI를 Response에 포함시켜 반환한다.

### REST API
- REST API는 Representational State Transfer API의 약자로, 간단히 설명하면 웹 애플리케이션이 제공하는 각각의 데이터를 리소스, 즉 자원으로 간주하고 각각의 자원에 고유한 URI(Uniform Resource Identifier)를 할당함으로써 이를 표현하는 API를 정의하기 위한 소프트웨어 아키텍처 스타일이다.

---

- 의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

- 테스트 코드
```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists());

    }
}
```
- `_links`는 HEATEOAS를 구현하기 위해 스프링 부트에서 생성한 JSON name이다. 그 뒤의 self는 자기 참조를 뜻하는 것을 JSON을 통해서 나타낸 것이다.


---

- 소스 코드
- `DemospringhateoasApplication.java`
```java
@SpringBootApplication
public class DemospringhateoasApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemospringhateoasApplication.class, args);
    }

}
```

- `Hello.java`
```java
public class Hello {

    private String prefix;

    private String name;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return prefix+ " " + name;
    }
}

```

- `SampleController.java`
```java
@RestController
public class SampleController {

    @GetMapping("/hello")
    public EntityModel<Hello> hello(){
        Hello hello = new Hello();
        hello.setPrefix("Hey, ");
        hello.setName("Kwon");

        EntityModel<Hello> helloEntityModel = new EntityModel<>(hello);
        helloEntityModel.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());

        return helloEntityModel;
    }
}
```
- EntityModel 객체에 HATEOAS를 구현하기 위해 `/hello` URL의 링크 정보를 추가한다.
- withSelfRel 메소드로 해당 URL이 자기 참조인 것을 나타낸다.


`Body = {"prefix":"Hey, ","name":"Kwon","_links":{"self":{"href":"http://localhost/hello"}}}`
- 테스트를 돌려보면 다음과 같이 추가된 링크 정보가 출력되는 것을 볼 수 있다.



> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>
