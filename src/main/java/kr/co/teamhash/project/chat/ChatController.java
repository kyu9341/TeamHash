package kr.co.teamhash.project.chat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kr.co.teamhash.domain.repository.ProjectRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.teamhash.account.CurrentUser;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Chat;
import kr.co.teamhash.domain.entity.ChatMemberDTO;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.entity.ProjectMember;
import kr.co.teamhash.project.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatservice;
    private final ProjectService projectService;

    @GetMapping("/project/{nickname}/{title}/chatting") // 첫 화면 매핑
    public String index(Model model, @PathVariable("nickname") String nickname,
                        @PathVariable("title") String title, @CurrentUser Account account) {

        // nickname과 projectTitle로 projectId 찾기
        Project project = projectService.getProject(nickname, title);
        // 채팅에서의 유저 이미지 매핑을 위해
        // 해당 프로젝트의 모든 유저 이미지와 이름을 가져온다
        List<ProjectMember> projectMember = project.getMembers();
        List<ChatMemberDTO> members = new ArrayList<ChatMemberDTO>();
        for (ProjectMember member : projectMember) {
            Account data = member.getAccount();
            members.add(new ChatMemberDTO(data.getEmail(),
                    data.getNickname(),
                    data.getIntroduction(),
                    data.getSchool(),
                    data.getProfileImage()));
        }


        List<Chat> chatList = chatservice.getChatList(project.getId()); // 채팅 리스트 추출
        //List<Chat> chatList = null;
        model.addAttribute(project);
        model.addAttribute("chatList", chatList);// 추출된 채팅 리스트 전달
        model.addAttribute("account", account);
        model.addAttribute("members", members);
        model.addAttribute("projectId", project.getId());
        return "project/chatting";
    }


    @MessageMapping("/chat/{projectId}")// 메세지가 목적지(/chat)로 전송되면 chat()메서드를 호출한다.
    @SendTo("/receive/chat/{projectId}")// 결과를 리턴시키는 목적지
    public Chat chat(@DestinationVariable Long projectId, Chat chat) throws Exception {

        chat.setSendDateTime(LocalDateTime.now()); // DB의 채팅에는 시간이 자동으로 저장 되지만
        // JavaScript에서 출력을 해주기 위해 값을 한번 넣어준다.
        chat.setProjectId(projectId);
        chatservice.saveChat(chat); // 전송 전 DB에 저장

        return chat;
    }

}