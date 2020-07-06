package kr.co.teamhash.main;

import kr.co.teamhash.account.CurrentUser;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.ProjectMember;
import kr.co.teamhash.domain.entity.Schedule;
import kr.co.teamhash.domain.entity.ScheduleDTO;
import kr.co.teamhash.domain.repository.AccountRepository;
import kr.co.teamhash.project.form.ProjectBuildForm;
import kr.co.teamhash.project.ProjectService;
import kr.co.teamhash.project.validator.ProjectBuildValidator;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProjectService projectService;
    private final AccountRepository accountRepository;
    private final ProjectBuildValidator projectBuildValidator;

    @InitBinder("projectBuildForm")
    public void projectInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(projectBuildValidator);
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }


    // 유저 메인 페이지에서 프로젝트의 리스트를 받기 위해
    // projectService에서 받은 뒤 템플릿으로 전송
    @GetMapping("/main")
    public String main(@CurrentUser Account account, Model model){
        if(account != null){
            Account byNickname = accountRepository.findByNickname(account.getNickname());
            List<ProjectMember> projectList = byNickname.getProjects();
            List<ScheduleDTO> scheduleDTO = new ArrayList<ScheduleDTO>();

            // account가 속해있는 프로젝트에서 모든 스케쥴을 가져온다.
            for(ProjectMember project : projectList){
                for (Schedule schedule : project.getProject().getSchedules()) {
                    scheduleDTO.add(new ScheduleDTO(schedule.getId(),
                                        schedule.getDate(),
                                        schedule.getTitle(),
                                        schedule.getContent(),
                                        schedule.getColor()));
                }
        
            }

            model.addAttribute("projectBuildForm", new ProjectBuildForm());
            model.addAttribute("projectList", projectList);
            model.addAttribute("schedules", scheduleDTO);
            model.addAttribute(account);

        }
        return "main";
    }


    @PostMapping("/main")
    public String buildProjectDone(@Valid @ModelAttribute ProjectBuildForm projectBuildForm, Errors errors,
                                   Model model , @CurrentUser Account account){
        if (errors.hasErrors()) {
//            List<Project> projectList = projectService.getProjectList(account);
            Account byNickname = accountRepository.findByNickname(account.getNickname());
            List<ProjectMember> projectList = byNickname.getProjects();
            model.addAttribute("projectBuildForm", new ProjectBuildForm());
            model.addAttribute("projectList", projectList);
            model.addAttribute(new ProjectBuildForm());
            model.addAttribute(account);
            model.addAttribute("error", "이미 사용중인 프로젝트명 입니다.");
            return "main";
        }

        projectService.saveNewProject(projectBuildForm, account);
        return "redirect:/main";
    }

}
