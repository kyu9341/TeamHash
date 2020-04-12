## 스프링 부트 ExceptionHandler
- 스프링 부트는 에러 핸들러기 기본적으로 등록이 되어있다.
- 스프링 부트가 제공하는 기본 예외 처리기
  - BasicErrorController
    - HTML과 JSON 응답 지원
  - 커스터마이징 방법
    - EroorController구현


- SpringbootexceptionApplication.java
```java
@SpringBootApplication
public class SpringbootexceptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootexceptionApplication.class, args);
    }

}
```
- SampleException.java
```java
public class SampleException extends RuntimeException {
}
```


- AppError.java : Exception발생 시 해당 Exception에 대한 정보를 저장
```java
public class AppError {
    private String message;

    private String reason;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
```

- SampleController.java
```java
@Controller
public class SampleController {
    @GetMapping("/hello")
    public String hello(){
        throw new SampleException();
    }

    @ExceptionHandler(SampleException.class)
    public @ResponseBody AppError sampleError(SampleException e){
        AppError appError = new AppError();
        appError.setMessage("error.app.key");
        appError.setReason("IDK IDK IDK");
        return appError;
    }
}
```
- /hello 라는 요청이 들어오면 SampleException을 발생시킨다.
  - @ExceptionHandler 어노테이션이 해당 예외를 받아서 처리
- appError객체에 메시지를 넣고 반환한다. (JSON형식)

<div style="width: 400px; height: 120px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/exception1.png" style="width: 400px
    ; height: 120px;">
</div>
- 위와 같이 JSON형태로 반환되는 것을 볼 수 있다.

### 커스텀 에러 페이지
- 상태 코드 값에 따라 에러 페이지 보여줄 수 있다.
- src/main/resources/(static or templates)/error/ 와 같은 경로에 html파일을 작성하면 된다.
  - 이때, 404와 같이 파일명이 상태 코드와 같거나 5xx같이 패턴을 맞추어 파일명을 만들어야 한다.
---
- 위에서 작성했던 SampleController에서 @ExceptionHandler 부분을 주석으로 변경하여 500번대 에러를 발생시킬 수 있도록 한다.

```Java
@Controller
public class SampleController {
  @GetMapping("/hello")
  public String hello(){
      throw new SampleException();
  }

/*    @ExceptionHandler(SampleException.class)
  public @ResponseBody AppError sampleError(SampleException e){
      AppError appError = new AppError();
      appError.setMessage("error.app.key");
      appError.setReason("IDK IDK IDK");
      return appError;
  }*/
}
```
---
- resources/static/ 의 경로에 다음과 같이 html파일을 작성한다.

- 404.html
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>404</h1>
</body>
</html>
```
<div style="width: 400px; height: 120px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/exception2.png" style="width: 400px
    ; height: 120px;">
</div>
- `/`에 대한 페이지를 작성하지 않았기 때문에 Not Found(404)에러가 발생하여 404.html이 반환된 것을 확인할 수 있다.
---

- 5xx.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>5xx</h1>
</body>
</html>
```

<div style="width: 400px; height: 120px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/exception3.png" style="width: 400px
    ; height: 120px;">
</div>
- `/hello` 요청을 보내면 예외를 발생시키도록 작성해두었으므로 500번대 서버 에러가 발생하여 5xx.html 이 반환된 것을 볼 수 있다.







> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>
