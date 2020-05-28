package kr.co.teamhash.project;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kr.co.teamhash.account.CurrentUser;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Member;
import kr.co.teamhash.domain.entity.Problems;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProblemShareService problemShareService;
    private final ProjectBuildValidator projectBuildValidator;

    @InitBinder("projectBuildForm")
    public void projectInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(projectBuildValidator);
    }


    // 프로젝트 생성 form
    @GetMapping("/project/build-project")
    public String buildProject(Model model){
        model.addAttribute("projectBuildForm", new ProjectBuildForm());

//        model.addAttribute("nickName", nickName);
        return "project/build-project";
    }

    @PostMapping("/project/build-project")
    public String buildProjectDone(@Valid @ModelAttribute ProjectBuildForm projectBuildForm, Errors errors, Model model , @CurrentUser Account account){
        if (errors.hasErrors()) {
            return "project/build-project";
        }
        log.info("BuilderNick : " + projectBuildForm.getBuilderNick());
        projectService.saveNewProject(projectBuildForm, account);
        
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

    // 4. 기존 /projectId/title 형식의 url을 /userNickName/projectTitle로 변환하려 한다
    //      - html에서 get/post 동작의 url 수정
    //      - controller에서 mapping되는 url 수정
    //      - controller에서 projectId로 값을 찾던 것 수정
    //      - service에서 projectId로 검색하던 것을 title로 검색하게 수정
    //          - 기존 서비스를 모두 수정하지 않고 userNickName에서
    //            해당 projectTitle의 id를 추출해 보내주는 것으로 수정하자.
    @GetMapping("/project/{nickname}/{title}")
    public String projectMain(@PathVariable("nickname") String nickname, @PathVariable("title") String title, Model model, @CurrentUser Account account){
        



        // nickname과 projectTitle로 projectId 찾기
        Long projectId=projectService.getProjectId(nickname, title);

        // nickname과 projectTitle에 맞는 프로젝트가 없을 때
        if(projectId == null)
            return "project/no-project";



        //프로젝트 검색
        if(!projectService.getProject(projectId).isPresent())
            return "project/no-project";

        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(projectId,account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);
        model.addAttribute("nickname", nickname);

        return "project/project-main";
    }

    // 문제 공유란 메인 페이지
    @GetMapping("/project/{nickname}/{title}/problem-share")
    public String problemShareMain(@PathVariable("nickname") String nickname, @PathVariable("title") String title, Model model,  @CurrentUser Account account){


        
        // nickname과 projectTitle로 projectId 찾기
        Long projectId=projectService.getProjectId(nickname, title);

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
        
        model.addAttribute("nickname", nickname);
        
        List<Problems> problemList = problemShareService.getProblemList(projectId);

        model.addAttribute("problemList", problemList);


        return "project/problem-share";
    }


    // 문제 공유란 글쓰기
    @GetMapping("/project/{nickname}/{title}/problem-share/post")
    public String write(@PathVariable("nickname") String nickname, @PathVariable("title") String title, Model model,  @CurrentUser Account account) {
        
        // nickname과 projectTitle로 projectId 찾기
        Long projectId=projectService.getProjectId(nickname, title);

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

        model.addAttribute("nickname", nickname);
        return "project/post-problem";
    }


    // 문제 공유란 글 작성 완료
    @PostMapping("/project/{nickname}/{title}/problem-share/post")
    public String write(@PathVariable("nickname") String nickname, @PathVariable("title") String title, Model model,  @CurrentUser Account account, Problems problem) {
        
        // nickname과 projectTitle로 projectId 찾기
        Long projectId=projectService.getProjectId(nickname, title);

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

        model.addAttribute("nickname", nickname);
        Long problemId = problemShareService.saveProblem(problem,projectId);




        return "redirect:/project/"+nickname+"/"+title+"/problem-share/"+problemId;
    }

    // 문제 공유글 디테일
    @GetMapping("/project/{nickname}/{title}/problem-share/{problemId}")
    public String problemDetail(@PathVariable("nickname") String nickname, @PathVariable("title") String title, @PathVariable("problemId") Long problemId, Model model,  @CurrentUser Account account){
        


        // nickname과 projectTitle로 projectId 찾기
        Long projectId=projectService.getProjectId(nickname, title);

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
      
        model.addAttribute("nickname", nickname);  
        Problems problem = problemShareService.getProblem(problemId);

        model.addAttribute("problem", problem);
        return "/project/problem-detail";
    }
    

    // 문제 공유글 수정란
    @GetMapping("/project/{nickname}/{title}/problem-share/edit/{problemId}")
    public String problemEdit(@PathVariable("nickname") String nickname, @PathVariable("title") String title, @PathVariable("problemId") Long problemId, Model model,  @CurrentUser Account account){

        // nickname과 projectTitle로 projectId 찾기
        Long projectId=projectService.getProjectId(nickname, title);

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

        model.addAttribute("nickname", nickname);
        Problems problem = problemShareService.getProblem(problemId);

        model.addAttribute("problem", problem);


        return "/project/problem-edit";
    }

    @PutMapping("/project/{nickname}/{title}/problem-share/{problemId}")
    public String problemUpdate(@PathVariable("nickname") String nickname, @PathVariable("title") String title, Model model,  @CurrentUser Account account, Problems problem){
        
        // nickname과 projectTitle로 projectId 찾기
        Long projectId=projectService.getProjectId(nickname, title);

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

        model.addAttribute("nickname", nickname);
        Long problemId = problemShareService.saveProblem(problem,projectId);
    
    
    
        return "redirect:/project/"+projectId+"/"+title+"/problem-share/"+problemId;
    }

    @DeleteMapping("/project/{nickname}/{title}/problem-share/{problemId}")
    public String problemDelete(@PathVariable("nickname") String nickname, @PathVariable("title") String title,@PathVariable("problemId") Long problemId, @CurrentUser Account account, Model model){
        
        // nickname과 projectTitle로 projectId 찾기
        Long projectId=projectService.getProjectId(nickname, title);

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

        model.addAttribute("nickname", nickname);
        problemShareService.deleteProblem(problemId);

        return "redirect:/project/"+projectId+"/"+title+"/problem-share";
    }
}
