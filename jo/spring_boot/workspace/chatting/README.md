# Using WebSocket to build an interactive web application  

브라우저와 서버 사이에서 메세지를 주고받는 간단한 웹 어플리케이션  

웹소켓은 얇고 가벼운 TCP 위에 존재하는 계층이다.

문자기능을 내장시키기 적당한 subprotocol을 만든다.

STOMP(Streaming Text Oriented Messaging Protocol)를 사용하여 진행한다.  

## Create a Resource Representation Class

서비스 상호작용에 대해서 생각해보자  

서비스는 이름이 포함된 JSON 형테의 STOMP 메세지를 받을 것이다.  

만약 이름이 Fred라면 메세지는 다음과 같을 것이다.  

~~~javascript
{
    "name" : "Fred"
}
~~~  

메세지를 담고있는 모델을 만들기 위해 우리는 name 특성과  getName() 메서드를 가지고 있는 평범한 자바 객체를 생성할 수 있다.  

메세지를 전송하고 이름을 추출할 때 서비스는 메세지의 내용을 생성하며 동작 할 것이다. 메세지의 내용 또한 JSON 객체일 것이다.  

~~~javascript
{
    "content" : "hello, Fred!"
}
~~~  

메세지 내용을 추출하는 메서드도 생성한다.  

Spring은 Jackson JSON 라이브러리를 사용하여 자동적으로 Greeting 인스턴스를
JSON으로 변환해준다.

## Create a Message-handling Controller
Spring은 STOMP 메세지로 작업한다.  

STOMP 메세지는 @Controller 클래스에서 연결된다.  

## Configure Spring for STOMP messaging

WebScoket과 STOMP 메세지를 사용해서 본질적인 서비스를 생성해본다.  


