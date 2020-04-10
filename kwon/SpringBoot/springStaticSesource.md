# 정적 리소스 지원


정적 리소스 맵핑 “​/​”
- 기본 리소스 위치
  - classpath:/static
  - classpath:/public
  - classpath:/resources/
  - classpath:/META-INF/resources
  - 예) “/hello.html” => /static/hello.html
  - spring.mvc.static-path-pattern: 맵핑 설정 변경 가능
  - spring.mvc.static-locations: 리소스 찾을 위치 변경 가능
- Last-Modified 헤더를 보고 304 응답을 보냄.
- ResourceHttpRequestHandler가 처리함.
  - WebMvcConfigurer의 addRersourceHandlers로 커스터마이징 할 수 있음


- **304 상태 코드** : 서버의 변경사항이 없음
- 파일이 변경되면 Last_Modified가 변경되는데 이것을 감지하면 리소스를 클라이언트에게 새로 보냄.
- 서버 변경사항이 없을 때 다시 요청을 보내면 304 코드로 응답하며 리소스를 새로 보내지 않는다. 따라서 응답이 훨씬 빨라진다.

- 기본적으로 resource들은 root부터 매핑이 되어있는데, 이것을 변경하고 싶다면 application.properties에서 다음과 같이 변경하면 된다.

```properties
spring.mvc.static-path-pattern=/static/**
```
이렇게 변경한다면 다음과 같이 /static/hello.html 로 요청해야 정상적으로 응답이 된다.
<div style="width: 600px; height: 80px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/tree/master/kwon/image/static1.png" style="width: 600px
    ; height: 80px;">
</div>




> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>
