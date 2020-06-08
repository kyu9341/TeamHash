package kr.co.teamhash.project.chat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private ChatService chatservice;



    
//   @GetMapping("") // 첫 화면 매핑
//   public String index(Model model){
//     List<ChatDto> chatDtoList = chatservice.getChatList(); // 채팅 리스트 추출

//     model.addAttribute("chatList",chatDtoList);// 추출된 채팅 리스트 전달

//     return "index";
//   }
}