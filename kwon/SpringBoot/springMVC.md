

# 스프링 부트 웹 MVC 컨트롤러 구현

인텔리제이에서 Spring Initializr를 통해 Web을 선택하고 프로젝트를 생성하면 pom.xml에 다음과 같은 의존성들이 자동으로 추가되어 있을 것이다.
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

```

- 테스트 코드
```java
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc; // 웹 MVC테스트를 만들 때 주로 사용, @WebMvcTest 어노테이션 사 자동으로 빈으로 만들어줌

    @Test
    public void hello() throws Exception {
    mockMvc.perform(get("/hello"))
    .andExpect(status().isOk())
    .andExpect(content().string("hello"));

}
```
위의 get에서 빨간줄이 생긴다면 아래의 두 방법으로 대체할 수 있다.

1.
```java
mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
        .andExpect(status().isOk())
        .andExpect(content().string("hello"));
```

2.
```java
MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/hello");
mockMvc.perform(builder)
        .andExpect(status().isOk())
        .andExpect(content().string("hello"));
```

메인 패키지 아래에 user패키지에 UserController를 생성

- 소스 코드
```java
@SpringBootApplication
public class DemospringmvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemospringmvcApplication.class, args);
    }

}
```
```java
@RestController
public class UserController {
  // @ResponseBody가 생략되어 있음
    @GetMapping("/hello")
    public String hello(){
      // 그냥 @Controller라면 @ResponseBody를 꼭 붙여주어야 함
      // 그래야 MessageConverter가 적용이 된다.
      // 그냥 @Controller인 경우에는 ViewResolver를 통 hello라는 이름에 해당하는 뷰를 찾으려 시도한다.
      // 하지만 @RestController가 붙어있으면 자동적으로 StringMessageConverter가 사용되어
       // HTTP 응답 본문에 들어가게 된다.
        return "hello";
    }
}
```
위와 같이 특별한 설정 없이 스프링 부트 MVC개발을 바로 시작할 수 있다.
스프링 부트에서 제공해주는 기본 설정 덕분 - (WebMvcAutoConfiguration)

- @RestController
  - @RestController 어노테이션은 스프링 4점대 버전부터 지원하는 어노테이션으로, 컨트롤러 클래스에 @RestController 만 붙이면 메서드에 @ResponseBody 어노테이션을 붙이지 않아도 문자열과 JSON 등을 전송할 수 있다. 뷰를 리턴하는 메서드들을 가지고 있는 @Controller와는 다르게 @RestController는 문자열, 객체 등을 리턴하는 메서드들을 가지고 있다.

## HttpMessageConverters
- HTTP 요청 본문을 객체로 변경하거나, 객체를 HTTP 응답 본문으로 변경할 때 사용.
  - {“username”:”keesun”, “password”:”123”} <-> User
    - @ReuqestBody
    - @ResponseBody
- HttpMessageConverters는 스프링 프레임워크에서 제공하는 인터페이스며 스프링 MVC의 일부분
- 만일 HTTP에 content-type으로 JSON이 들어오면 (또한 본문도 JSON) JsonConverter로 바뀜
- content-type이 문자열이면 StringMessageConverter가 사용됨


- 테스트 코드
```java
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    // user를 생성하는 테스트
    @Test
    public void createUser_JSON() throws Exception {
        String userJson = "{\"username\":\"kwon\", \"password\":\"123\"}";
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(equalTo("kwon"))))
                .andExpect(jsonPath("$.password", is(equalTo("123"))));
    }

}
```

- 소스 코드
```java
public class User {
    private Long id;

    private String username;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```
```java
@RestController
public class UserController {

  // @ResponseBody가 생략되어 있음
    @GetMapping("/hello")
    public String hello(){
      // 그냥 @Controller라면 @ResponseBody를 꼭 붙여주어야 함
      // 그래야 MessageConverter가 적용이 된다.
      // 그냥 @Controller인 경우에는 ViewResolver를 통 hello라는 이름에 해당하는 뷰를 찾으려 시도한다.
      // 하지만 @RestController가 붙어있으면 자동적으로 StringMessageConverter가 사용되어
       // HTTP 응답 본문에 들어가게 된다.
        return "hello";
    }

    @PostMapping("/users/create")
    public User create(@RequestBody User user){
        return user;
    }
}
```


## ViewResolver
- **ViewResolver** : 스프링에서 Controller에서 반환한 값(ModelAndView 혹은 Model)을 통해 뷰를 만드는 역할
- **ContentNegotiatingViewResolver** : 동일한 URI에서 HTTP Request에 있는 Content-type 및 Accept 헤더를 기준으로 다양한 Content-type으로 응답할 수 있게 하는 ViewResolver

응답을 XML로 바꾸어 보아도 정상적으로 동작한다. 단, XML로 내보내고 싶은 경우에는 다음의 의존성을 추가해야 한다.
```xml
<dependency>
   <groupId>com.fasterxml.jackson.dataformat</groupId>
   <artifactId>jackson-dataformat-xml</artifactId>
   <version>2.9.6</version>
</dependency>
```

- 테스트 코드
```java
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    // user를 생성하는 테스트
    @Test
    public void createUser_JSON() throws Exception {
        String userJson = "{\"username\":\"kwon\", \"password\":\"123\"}";
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(equalTo("kwon"))))
                .andExpect(jsonPath("$.password", is(equalTo("123"))));
    }

    @Test
    public void createUser_XML() throws Exception {
        String userJson = "{\"username\":\"kwon\", \"password\":\"123\"}";
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_XML)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(xpath("/User/username").string("kwon"))
                .andExpect(xpath("/User/password").string("123"));
    }

}

```



> 참조
>
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>
> <https://webcoding.tistory.com/entry/Spring-REST-API-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0>
