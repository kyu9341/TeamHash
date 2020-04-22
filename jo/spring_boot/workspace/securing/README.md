# Securing Web
Spring Security로 보호할 수 있는 웹 어플리케이션을 제작한다. 


## Unsecured Web Application
web application에 보안을 적용하기 전에 보안을 적용할 web application이 필요하다.  

먼저 보안이 적용되지 않은 web Application을 작성해 두고 시작한다.  


### home.html 
~~~html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org" xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
    <head>
        <title>Spring Security Example</title>
    </head>
    <body>
        <h1>Welcome!</h1>

        <p>Click <a th:href="@{/hello}">here</a> to see a greeting.</p>
    </body>
</html>
~~~

### hello.html
~~~html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
    <head>
        <title>Hello World!</title>
    </head>
    <body>
        <h1>Hello world!</h1>
    </body>
</html>
~~~
--- 
Web Application은 Spring MVC에 기반을 두었다. 이에 따라 우리는 Spring MVC 설정이 필요하며 위의 템플릿들을 보여주기 위해 컨트롤러를 설정해야 된다.  

## Set up
만약 우리가 허가되지 않은 유저를 페이지에 접근하는 것을 막는것을 원할 때 지금처럼 방문자가 링크를 클릭하여 페이지를 보는 것을 막지 앟는다면  
우리는 방문자가 로그인 한 뒤에 페이지를 볼 수 있도록 해야아한다.  

Spring Security를 우리의 Application에서 설정해야 하는데 만약 Spring Security가 classpath에 존재한다면 Spring Boot는 자동적으로 secures를 모든 HTTP endpoint에 적용한다.

Security 설정은 WebSecurityConfig.java에서 한다.

### login.html

~~~html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
    <head>
        <title>Spring Security Example </title>
    </head>
    <body>
        <div th:if="${param.error}">
            Invalid username and password.
        </div>
        <div th:if="${param.logout}">
            You have been logged out.
        </div>
        <form th:action="@{/login}" method="post">
            <div><label> User Name : <input type="text" name="username"/> </label></div>
            <div><label> Password: <input type="password" name="password"/> </label></div>
            <div><input type="submit" value="Sign In"/></div>
        </form>
    </body>
</html>
~~~

로그인이 진행되는 페이지

Thymeleaf 템플릿이며 유저 이름과 비밀번호를 받아서 `/login`으로 전송한다.  

Spring Security는 사용자의 요청과 인증을 가로채는 필터를 제공한다.  

만약 사용자가 인증에 실패하면 해당 페이지는 `/login?error`를 redirected 해주며 적당한 에러메세지를 보여준다.  

성공적으로 로그아웃을 하면 application은 `/login?logout`을 전송하고 페이지는 적당한 성공 메세지를 보여준다.  

### hello.html

마지막으로 우리는 방문자에게 현재 사용자의 이름을 보여주며 로그아웃 할 수 있는 화면을 제공한다.

hello 페이지를 다음과 같이 수정하여 현재 사용자에게 hello메세지를 보내며 로그아웃할 수 있도록 페이지를 제공해준다.  

~~~html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
    <head>
        <title>Hello World!</title>
    </head>
    <body>
        <h1 th:inline="text">Hello [[${#httpServletRequest.remoteUser}]]!</h1>
        <form th:action="@{/logout}" method="post">
            <input type="submit" value="Sign Out"/>
        </form>
    </body>
</html>
~~~








# Reference
https://spring.io/guides/gs/securing-web/

https://ryudung.tistory.com/20
