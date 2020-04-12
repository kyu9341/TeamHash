## 스프링 부트 Thymeleaf, HtmlUnit

### Thymeleaf
- 스프링 부트가 자동 설정을 지원하는 템플릿 엔진
  - FreeMarker
  - Groovy
  - Thymeleaf
  - Mustche
- 스프링 부트에서 JSP를 권장하지 않는 이유
  - 스프링 부트는 독립적으로 실행가능한 임베디드 톰캣으로 애플리케이션을 빠르고 쉽게 만들어서 배포하길 바란다.
  - JSP를 사용하면 JAR 패키징 할 때는 동작하지 않고, WAR 패키징을 해야 한다.
  - Undertow는 JSP를 지원하지 않는다.

---
- 기본적으로 자동설정이 적용이 되면 동적으로 생성되는 뷰는 resources/templates에서 찾게 된다.

- Thymeleaf를 사용하려면 다음과 같은 의존성을 추가해야 한다.
```
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tymeleaf</artifactId>
  </dependency>
```

---

- 테스트 코드 작성
```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        // 요청 "/hello"
        // - 응답 모델 name : kwon
        // - 뷰 이름 : hello
        // 뷰의 이름과 모델의 데이터, 렌더링 된 결과를 확인하는 테스트
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(status().isOk())
                .andDo(print()) // 렌더링 결과 확인 (타임리프라 가능)
                .andExpect(view().name("hello")) // 뷰의 이름
                .andExpect(model().attribute("name", is("kwon"))) // 모델의 데이터
                .andExpect(content().string(containsString("kwon")));
    }
}
```
- 뷰의 이름과 모델의 데이터, 렌더링 된 결과를 확인하는 테스트
- Thymeleaf에서 렌더링한 HTML을 확인할 수 있다.

---

- 소스 코드 작성
- SpringbootmvcApplication.java
```java
@SpringBootApplication
public class SpringbootmvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootmvcApplication.class, args);
    }
}
```
- SampleController.java
```java
@Controller
public class SampleController {

    @GetMapping("/hello")
    public String hello(Model model){
        // 여기서 리턴하는 String은 뷰의 이름
        // @RestController가 아니기 때문에 응답의 본문을 리턴하는 것이 아님
        // 데이터는 model에 담음
        model.addAttribute("name", "kwon");
        return "hello";
    }
}
```
- Model 객체에 name:kwon을 추가

---

- 템플릿 작성
- resources/tamplates/hello.html
```html
<!DOCTYPE html>
<!--xml 네임스페이스를 추가해야함(th)-->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1 th:text="${name}">Name</h1>

</body>
</html>
```
- Thymeleaf를 사용하려면 `xmlns:th="http://www.thymeleaf.org"`를 추가하는 것이 반드시 필요한다.
- 값을 성공적으로 받아온다면 `${name}`에 넘어온 kwon이 출력될 것이고, 아니라면 Name가 출력될 것이다.


<div style="width: 400px; height: 120px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/thymeleaf1.png" style="width: 400px
    ; height: 120px;">
</div>

---

### HtmlUnit
- HtmlUnit : HTML을 단위테스트 하기 위한 툴
- 템플릿 뷰 테스트에 유용


- 다음과 같은 htmlunit 의존성을 추가한다. (scope이 테스트 이므로 테스트할 때만 사용)
```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>htmlunit-driver</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>net.sourceforge.htmlunit</groupId>
    <artifactId>htmlunit</artifactId>
    <scope>test</scope>
</dependency>
```

- 테스트 코드
```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    WebClient webClient;

    @Test
    public void hello() throws Exception {
        HtmlPage page = webClient.getPage("/hello");
        HtmlHeading1 h1 = page.getFirstByXPath("//h1");
        assertThat(h1.getTextContent()).isEqualToIgnoringCase("kwon");
    }
}
```
- h1태그의 정보를 얻어서 테스트를 수행한다.




> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>
