package kr.co.teamhash.main;

import kr.co.teamhash.account.CurrentUser;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.project.ProjectService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProjectService projectService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/main")
    public String main(@CurrentUser Account account, Model model){
        if(account != null){
            List<Project> projectList = projectService.getProjectList(account);
            model.addAttribute("projectList", projectList);
            model.addAttribute(account);

        }
        return "main";
    }

}
