package com.springboot.chatting.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.springboot.chatting.domain.entity.Chat;
import com.springboot.chatting.dto.ChatDto;
import com.springboot.chatting.service.ChatService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

  /*
  @MessageMapping("/hello") 
                            // 메세지의 유효 적제량은 greeting()메서드를 통과하는 HelloMessage 객체의 경계에 있다.
  @SendTo("/topic/greetings")
  public Greeting greeting(HelloMessage message) throws Exception {
    //Thread.sleep(1000); // simulated delay
                        // 내부적으로 클라이언트가 메세지를 전송하고 나서 서버가 비동기로 동작할 때 딜레이가 필요하다.
                        // 클라이언트는 응답을 기다릴 필요가 없이 동작이 가능하다.

                        // 딜레이가 지난 뒤 greeting() 메서드는 Greeting 객체를 생성하고 반환한다.
                        // 
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }
  */

  private ChatService chatservice;

  public ChatController(ChatService chatservice){
    this.chatservice = chatservice;
  }

  @GetMapping("") // 첫 화면 매핑
  public String index(Model model){
    List<ChatDto> chatDtoList = chatservice.getChatList(); // 채팅 리스트 추출

    model.addAttribute("chatList",chatDtoList);// 추출된 채팅 리스트 전달

    return "index";
  }


  @MessageMapping("/chat")// 메세지가 목적지(/chat)로 전송되면 chat()메서드를 호출한다.
  @SendTo("/topic/chat")// 결과를 리턴시키는 목적지
  public Chat chat(ChatDto chatDto) throws Exception{
    long pji = 3; // projectId 향후 사용자의 정보를 읽어와서 넣을 것
    
    chatDto.setProjectId(pji);
    chatDto.setSendDateTime(LocalDateTime.now()); // DB의 채팅에는 시간이 자동으로 저장 되지만
                                                  // JavaScript에서 출력을 해주기 위해 값을 한번 넣어준다.

    chatservice.saveChat(chatDto); // 전송 전 DB에 저장

    return chatDto.toEntity();
  }

}