package kr.co.teamhash.project;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kr.co.teamhash.account.CurrentUser;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Member;
import kr.co.teamhash.domain.entity.Problems;


@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProblemShareService problemShareService;
    private final ProjectBuildValidator projectBuildValidator;

    @InitBinder
    public void projectInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(projectBuildValidator);
    }


    // 프로젝트 생성 form
    @GetMapping("/project/build-project")
    public String buildProject(Model model){
        model.addAttribute("projectBuildForm", new ProjectBuildForm());

//        model.addAttribute("nickName", nickName);
        return "project/buildProject";
    }

    @PostMapping("/project/build-project")
    public String buildProjectDone(@Valid @ModelAttribute ProjectBuildForm projectBuildForm, Errors errors, Model model , @CurrentUser Account account){
        if (errors.hasErrors()) {
            return "project/buildProject";
        }
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
        if(!projectService.getProject(projectId).isPresent())
            return "project/noProject";

        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(projectId,account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);

        return "project/projectMain";
    }

    // 문제 공유란 메인 페이지
    @GetMapping("/project/{projectId}/{title}/problem_share")
    public String problem_share_main(@PathVariable("projectId") Long projectId, @PathVariable("title") String title, Model model,  @CurrentUser Account account){

        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(projectId,account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);
        
        List<Problems> problemList = problemShareService.getProblemList(projectId);

        model.addAttribute("problemList", problemList);


        return "project/problem_share";
    }


    // 문제 공유란 글쓰기
    @GetMapping("/project/{projectId}/{title}/problem_share/post")
    public String write(@PathVariable("projectId") Long projectId, @PathVariable("title") String title, Model model,  @CurrentUser Account account) {
        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(projectId,account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);

        return "project/post_problem";
    }


    // 문제 공유란 글 작성 완료
    @PostMapping("/project/{projectId}/{title}/problem_share/post")
    public String write(@PathVariable("projectId") Long projectId, @PathVariable("title") String title, Model model,  @CurrentUser Account account, Problems problem) {
        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(projectId,account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);

        Long problemId = problemShareService.saveProblem(problem,projectId);




        return "redirect:/project/"+projectId+"/"+title+"/problem_share/"+problemId;
    }

    // 문제 공유글 디테일
    @GetMapping("/project/{projectId}/{title}/problem_share/{problemId}")
    public String problemDetail(@PathVariable("projectId") Long projectId, @PathVariable("title") String title, @PathVariable("problemId") Long problemId, Model model,  @CurrentUser Account account){
        
        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(projectId,account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);
        
        Problems problem = problemShareService.getProblem(problemId);

        model.addAttribute("problem", problem);
        return "/project/problemDetail";
    }
    

    // 문제 공유글 수정란
    @GetMapping("/project/{projectId}/{title}/problem_share/edit/{problemId}")
    public String problemEdit(@PathVariable("projectId") Long projectId, @PathVariable("title") String title, @PathVariable("problemId") Long problemId, Model model,  @CurrentUser Account account){


                
        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(projectId,account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);

        Problems problem = problemShareService.getProblem(problemId);

        model.addAttribute("problem", problem);


        return "/project/problemEdit";
    }

    @PutMapping("/project/{projectId}/{title}/problem_share/{problemId}")
    public String problemUpdate(@PathVariable("projectId") Long projectId, @PathVariable("title") String title, Model model,  @CurrentUser Account account, Problems problem){
        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(projectId,account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);

        Long problemId = problemShareService.saveProblem(problem,projectId);
    
    
    
        return "redirect:/project/"+projectId+"/"+title+"/problem_share/"+problemId;
    }

    @DeleteMapping("/project/{projectId}/{title}/problem_share/{problemId}")
    public String problemDelete(@PathVariable("projectId") Long projectId, @PathVariable("title") String title,@PathVariable("problemId") Long problemId, @CurrentUser Account account, Model model){
        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(projectId,account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);

        problemShareService.deleteProblem(problemId);

        return "redirect:/project/"+projectId+"/"+title+"/problem_share";
    }
}
