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


# reference
https://spring.io/guides/gs/securing-web/

