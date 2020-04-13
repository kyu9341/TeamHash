## 스프링 부트 - CORS(Cross-Origin Resource Sharing)
- **Origin** : 아래의 세 가지 요소를 조합한 것을 하나의 Origin이라고 한다.
  - URI 스키마(http, https)
  - hostname(localhost 등..)
  - 포트(8080, 18080 .. )
- **Single-Origin Policy** 에서는 하나의 Origin이 다른 Origin을 서로 호출할 수 없다.
- 예를 들어, REST api를 제공하는 서버가 http://localhost:8080 에서 제공이 되고, 그 REST api를 http://localhost:18080 을 사용하는 application이 호출할 수 없다.

- Cross-Origin Resource Sharing 에서는 위와 같이 동일한 Origin이 아니더라도 서로 다른 Origin에서 자원을 요청하여 사용할 수 있게 해준다.
- CORS을 적용하려면 웹 어플리케이션에 그에 따른 처리를 해야하고 스프링 부트에서는 `@CrossOrigin` 어노테이션 혹은 WebConfig를 통해 CORS를 적용하는 방법을 제공한다.

#### Single-Origin Policy
- 두 개의 프로젝트롤 생성하여 하나는 서버로써 REST api를 간단하게 만들어 호출할 수 있도록 하고, 하나는 클라이언트로써 ajax를 통해 다른 Origin에서 자원을 요청하도록 만든다.

- 서버측 소스 `SpringcorsserverApplication.java`
```java
@SpringBootApplication
@RestController
public class SpringcorsserverApplication {

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringcorsserverApplication.class, args);
    }

}
```
- `/hello`로 요청을 보내면 Hello를 응답하는 api이다.

---

- 클라이언트측 소스
- `application.properties`에 `server.port=18080`를 추가한다.
- `SpringcorsclientApplication.java`
```java
@SpringBootApplication
public class SpringcorsclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcorsclientApplication.class, args);
    }

}
```
- `index.html`
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>CORS Client</h1>
<script src="/webjars/jquery/3.4.1/dist/jquery.min.js"></script>
<script>
    $(function(){
        $.ajax("http://localhost:8080/hello")
            .done(function (msg) {
                alert(msg);
            })
            .fail(function(){
                alert("fail")
            })
    })
</script>
</body>
</html>
```
- `http://localhost:8080/hello`로 ajax로 요청을 보내 성공했다면 응답받은 메세지를, 실패했다면 fail을 표시한다.

<div style="width: 900px; height: 120px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/cors1.png" style="width: 900px
    ; height: 120px;">
</div>

<div style="width: 800px; height: 50px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/cors2.png" style="width: 800px
    ; height: 50px;">
</div>

- 위와 같이 실패했다는 메세지와 함께 오류가 발생한 것을 볼 수 있다.
- `Access-Control-Allow-Origin`라는 해더를 서버가 보내주어야 한다. (어떤 Origin이 접근할 수 있는지 알려주는 해더)

#### CORS 적용 - @CrossOrigin
- 서버측 소스 `SpringcorsserverApplication.java`
```java
@SpringBootApplication
@RestController
public class SpringcorsserverApplication {

    @CrossOrigin(origins = "http://localhost:18080")
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringcorsserverApplication.class, args);
    }

}
```
- `@CrossOrigin(origins = "http://localhost:18080")`와 같이 어노테이션을 추가하여 허용할 Origin을 작성하면 된다.

<div style="width: 900px; height: 120px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/cors3.png" style="width: 900px
    ; height: 120px;">
</div>
- 이번에는 Hello라는 응답이 정상적으로 출력되는 것을 볼 수 있다.

#### CORS 적용 - WepConfig
- 설정을 위와 같이 어노테이션으로 모든 메소드에 하기 힘들다면 WepConfig 라는 설정파일을 작성하여 설정할 수 있다.

- 서버측 소스 `WebConfig.java`
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:18080");
    }
}
```
- `/**` : 모든 `http://localhost:18080`를 포함한 요청을 허용하도록 한다.
- 위와 같이 설정파일을 만들어서 사용하면 글로벌한 설정으로 cors를 적용할 수 있다.


> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>
