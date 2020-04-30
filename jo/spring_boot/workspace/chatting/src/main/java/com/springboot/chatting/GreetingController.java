package com.springboot.chatting;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


  @MessageMapping("/hello") // 메세지가 목적지(/hello)로 전송되면 greeting()메서드를 호출한다.
                            // 메세지의 유효 적제량은 greeting()메서드를 통과하는 HelloMessage 객체의 경계에 있다.
  @SendTo("/topic/greetings")
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
                        // 내부적으로 클라이언트가 메세지를 전송하고 나서 서버가 비동기로 동작할 때 딜레이가 필요하다.
                        // 클라이언트는 응답을 기다릴 필요가 없이 동작이 가능하다.

                        // 딜레이가 지난 뒤 greeting() 메서드는 Greeting 객체를 생성하고 반환한다.
                        // 
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }

}