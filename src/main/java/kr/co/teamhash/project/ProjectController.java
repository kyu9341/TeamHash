package kr.co.teamhash.project;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.repository.ProjectRepository;

import kr.co.teamhash.account.CurrentUser;
import kr.co.teamhash.domain.entity.Account;


@Controller
@RequiredArgsConstructor
public class ProjectController {


    private final ProjectRepository projectRepository;
    private final ProjectService projectService;


    @GetMapping("/build-project")
    public String buildProject(Model model){
        model.addAttribute("projectBuildForm", new ProjectBuildForm());
        return "project/buildProject";
    }

    @PostMapping("/build-project")
    public String buildProjectDone(@ModelAttribute ProjectBuildForm projectBuildForm, Model model , @CurrentUser Account account){
        
        Project project = projectService.saveNewProject(projectBuildForm,account);
        
        return "redirect:/main";
    }

}
