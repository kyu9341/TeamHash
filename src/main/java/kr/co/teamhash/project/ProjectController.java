package kr.co.teamhash.project;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import kr.co.teamhash.account.CurrentUser;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Member;


@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;


    // 프로젝트 생성 form
    @GetMapping("/build-project")
    public String buildProject(Model model){
        model.addAttribute("projectBuildForm", new ProjectBuildForm());
        return "project/buildProject";
    }

    @PostMapping("/build-project")
    public String buildProjectDone(@ModelAttribute ProjectBuildForm projectBuildForm, Model model , @CurrentUser Account account){
        
        projectService.saveNewProject(projectBuildForm,account);
        
        return "redirect:/main";
    }


    // 구현해야 하는 것
    // 1. 해당 프로젝트에 맞는 정보 전달
    //      - projectID를 통해 검사 후 없으면 noProject 페이지 반환
    // 2. 멤버가 아닌 유저의 접근 막기
    //      - member에서 해당 프로젝트의 멤버유저 검색
    // 3. 잘못된 경로 (없는 프로젝트 id, title) 막기
    //      - 해당 경로의 projectId가 존재하지 않는다면 404page 반환
    //      - title만 다른경우는 이후 수정
    @GetMapping("/project/{projectId}/{title}")
    public String projectMain(@PathVariable("projectId") Long projectId, @PathVariable("title") String title, Model model, @CurrentUser Account account){
        
        //프로젝트 검색
        if(projectService.getProject(projectId).isEmpty())
            return "project/noProject";

        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        List<Member> memberList = projectService.getMemberList(projectId);
        boolean isMember = false;
        for(Member member : memberList){
            if(member.getUserId() == account.getId()){
                isMember = true;
                break;
            }
        }
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);

        return "project/projectMain";
    }

}
