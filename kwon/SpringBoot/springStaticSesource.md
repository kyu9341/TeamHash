## Spring Boot 정적 리소스 지원

### 정적 리소스 맵핑 “​/​”
- 기본 리소스 위치
  - classpath:/static
  - classpath:/public
  - classpath:/resources/
  - classpath:/META-INF/resources
- Last-Modified 헤더를 보고 304 응답을 보냄.
- **304 상태 코드** : 서버의 변경사항이 없음
- 파일이 변경되면 Last_Modified가 변경되는데 이것을 감지하면 리소스를 클라이언트에게 새로 보냄.
- 서버 변경사항이 없을 때 다시 요청을 보내면 304 코드로 응답하며 리소스를 새로 보내지 않는다. 따라서 응답이 훨씬 빨라진다.


#### spring.mvc.static-path-pattern: 맵핑 설정 변경 가능

- 기본적으로 resource들은 root부터 매핑이 되어있는데, 이것을 변경하고 싶다면 application.properties에서 다음과 같이 변경하면 된다.
- 예) “/hello.html” => /static/hello.html

```properties
spring.mvc.static-path-pattern=/static/**
```
이렇게 변경한다면 다음과 같이 /static/hello.html 로 요청해야 정상적으로 응답이 된다.

<div style="width: 600px; height: 80px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/static1.png" style="width: 600px
    ; height: 80px;">
</div>


#### WebMvcConfigurer의 addRersourceHandlers로 커스터마이징
- com.kwon.demospringmvc/config/WebConfig 파일
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 기존에 제공하는 리소스 핸들러는 그대로 유지하며
    // 원하는 리소스 핸들러만 추가할 수 있음
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/m/**")
                .addResourceLocations("classpath:/m/") // 반드시 '/'로 끝나야 함
                .setCachePeriod(20); // 없어도 됨.(캐시 지속시간 설정 (초))
    }
}
```
- 위와 같이 작성하면 기존에 제공하는 리소스 핸들러는 그대로 유지하며 원하는 리소스 핸들러만 추가할 수 있음
- addResourceHandlers는 리소스 등록 및 핸들러를 관리하는 객체인 ResourceHandlerRegistry를 통해 리소스 위치와 이 리소스와 매칭될 url을 등록
- setCachePeriod : 캐시의 지속시간 설정(초)


- resource/m/hello2.html
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
Hello2~ Static Resource
</body>
</html>
```
이제 /m/hello2.html로 요청을 보내면 정상적인 응답을 받을 수 있다.

<div style="width: 600px; height: 80px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/static2.png" style="width: 600px
    ; height: 80px;">
</div>



> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>
