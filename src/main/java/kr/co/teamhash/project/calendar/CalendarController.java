package kr.co.teamhash.project.calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.teamhash.account.CurrentUser;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.project.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final ProjectService projectService;
    
    // 캘린더
    @GetMapping("/project/{nickname}/{title}/calendar")
    public String calendar(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                           Model model,  @CurrentUser Account account){
        
        // nickname과 projectTitle로 projectId 찾기
        Long projectId = projectService.getProjectId(nickname, title);

        // nickname과 projectTitle에 맞는 프로젝트가 없을 때
        if(projectId == null)
            return "project/no-project";

        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(projectId,account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);
        
        model.addAttribute("account", account);

        return "project/calendar";
    }

    // @PostMapping("/project/{nickname}/{title}/calendar/scedule")

}