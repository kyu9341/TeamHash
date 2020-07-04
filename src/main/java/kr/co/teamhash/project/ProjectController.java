package kr.co.teamhash.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.teamhash.domain.entity.*;
import kr.co.teamhash.domain.repository.AccountRepository;
import kr.co.teamhash.domain.repository.MemberRepository;
import kr.co.teamhash.domain.repository.ProjectRepository;
import kr.co.teamhash.notification.NotificationService;
import kr.co.teamhash.project.form.MemberForm;
import kr.co.teamhash.project.form.ProgressForm;
import kr.co.teamhash.project.form.ProjectBuildForm;
import kr.co.teamhash.project.validator.MemberValidator;
import kr.co.teamhash.project.validator.ProgressValidator;
import kr.co.teamhash.project.validator.ProjectBuildValidator;
import lombok.RequiredArgsConstructor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kr.co.teamhash.account.CurrentUser;

@Slf4j
@Controller
@RequestMapping("/project/{nickname}/{title}")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProblemShareService problemShareService;
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final NotificationService notificationService;
    private final MemberValidator memberValidator;
    private final ObjectMapper objectMapper;
    private final ProgressValidator progressValidator;

    @InitBinder("memberForm")
    public void memberInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberValidator);
    }

    @InitBinder("progressForm")
    public void progressInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(progressValidator);
    }

    @GetMapping("/main")
    public String projectMain(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                              Model model, @CurrentUser Account account){

        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);

        // nickname과 projectTitle에 맞는 프로젝트가 없을 때
        if(project == null)
            return "project/no-project";

        List<ProjectMember> members = memberRepository.findAllByProjectId(project.getId());

        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(project.getId(), account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute(project);
        model.addAttribute("members", members);
        model.addAttribute(account);

        return "project/project-main";
    }

    @GetMapping("/write")
    public String projectDescription(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                                     Model model, @CurrentUser Account account) {
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);
        boolean isMember = projectService.isMember(project.getId(), account);

        model.addAttribute("isMember", isMember);
        model.addAttribute(project);
        model.addAttribute(account);

        return "project/write";
    }

    @PostMapping("/write")
    public String projectDescriptionForm(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                                         String description ,Model model, @CurrentUser Account account) {
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);
        projectService.createDescription(project, description);
        return "redirect:/project/" + nickname +"/" + project.getEncodedTitle();
    }


    // 문제 공유란 메인 페이지
    @GetMapping("/problem-share")
    public String problemShareMain(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                                   Model model,  @CurrentUser Account account){
        // nickname과 projectTitle로 projectId 찾기
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);

        // nickname과 projectTitle에 맞는 프로젝트가 없을 때
        if(project == null)
            return "project/no-project";


        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(project.getId(), account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("title", title);
        model.addAttribute("nickname", nickname);
        
        List<Problems> problemList = problemShareService.getProblemList(project.getId());
        Collections.reverse(problemList);

        model.addAttribute("problemList", problemList);
        model.addAttribute("account", account);
        model.addAttribute("nowTime",  LocalDateTime.now());

        return "project/problem-share";
    }




    // 문제 공유란 글 작성 완료
    @PostMapping("/problem-share/post")
    public String write(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                        Model model,  @CurrentUser Account account, Problems problem) {
        
        // nickname과 projectTitle로 projectId 찾기
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);

        // nickname과 projectTitle에 맞는 프로젝트가 없을 때
        if(project == null)
            return "project/no-project";

        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(project.getId(), account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("title", title);
        model.addAttribute("nickname", nickname);

        problemShareService.saveProblem(problem, project.getId(), account);

        return "redirect:/project/"+nickname+"/"+ project.getEncodedTitle() +"/problem-share/";
    }

    // 코멘트 작성
    @PostMapping("/problem-share/comment")
    public String write(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                        Model model,  @CurrentUser Account account, Comment comment, Long pId) {
        
        // nickname과 projectTitle로 projectId 찾기
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);

        // nickname과 projectTitle에 맞는 프로젝트가 없을 때
        if(project == null)
            return "project/no-project";

        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(project.getId(), account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", project.getId());

        model.addAttribute("title", title);
        model.addAttribute("nickname", nickname);
        
        // 입력받은 comment 내용, 커멘트가 달린 문제공유글 id, 해당 코멘트를 작성한 유저 
        problemShareService.saveComment(comment, pId, account);

        return "redirect:/project/" + nickname + "/" + project.getEncodedTitle() + "/problem-share/";
    }

    //문제 공유글 삭제
    @DeleteMapping("/problem-share/{problemId}")
    public String problemDelete(@PathVariable("nickname") String nickname, @PathVariable("title")
            String title,@PathVariable("problemId") Long problemId, @CurrentUser Account account, Model model){
        
        // nickname과 projectTitle로 projectId 찾기
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);

        // nickname과 projectTitle에 맞는 프로젝트가 없을 때
        if(project == null)
            return "project/no-project";
        
        
        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(project.getId(),account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("projectId", project.getId());
        model.addAttribute("title", title);

        model.addAttribute("nickname", nickname);
        problemShareService.deleteProblem(problemId,account);

        return "redirect:/project/" + nickname + "/" + project.getEncodedTitle() + "/problem-share";
    }
    //코멘트 삭제
    @DeleteMapping("/problem-share/comment/{commentId}")
    public String commentDelete(@PathVariable("nickname") String nickname, @PathVariable("title")
            String title,@PathVariable("commentId") Long commentId, @CurrentUser Account account, Model model){
        
        // nickname과 projectTitle로 projectId 찾기
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);

        // nickname과 projectTitle에 맞는 프로젝트가 없을 때
        if(project == null)
            return "project/no-project";

        // 프로젝트의 맴버 리스트에 현재 유저의 아이디가 있다면 페이지 공개
        boolean isMember = projectService.isMember(project.getId(),account);

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute("isMember", isMember);
        model.addAttribute("title", title);

        model.addAttribute("nickname", nickname);
        problemShareService.deleteComment(commentId,account);

        return "redirect:/project/" + nickname + "/" + project.getEncodedTitle() + "/problem-share";
    }


    
    @GetMapping("/settings")
    public String settings(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                           Model model,  @CurrentUser Account account) throws JsonProcessingException {

        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);
        List<ProjectMember> members = memberRepository.findAllByProjectId(project.getId());

        model.addAttribute(account);
        model.addAttribute("members", members);
        model.addAttribute(project);
        model.addAttribute("progressForm", new ProgressForm());

        List<String> userList = accountRepository.findAll().stream().map(Account::getNickname).collect(Collectors.toList());
        model.addAttribute("whitelist", objectMapper.writeValueAsString(userList));

        List<Notification> sentInvitations = notificationService.getSentInvitations(project.getId());
        model.addAttribute("sentInvitations", sentInvitations.stream()
                .map(Notification::getAccount)
                .map(Account::getNickname).collect(Collectors.toList()));

        return "project/settings";
    }

    @PostMapping("/settings/add")
    @ResponseBody
    public ResponseEntity addMember(@RequestBody @Valid MemberForm memberForm,
                                    Errors errors, @PathVariable("title") String title,
                                    @PathVariable("nickname") String builderNick, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("error", "존재하지 않는 닉네임입니다.");
            return ResponseEntity.badRequest().build();
        }
        String memberNickname = memberForm.getMemberNickname();

        log.info("title: " + title);

        // 초대 알림 보냄
        notificationService.addNotification(memberNickname, title, builderNick);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/settings/remove")
    @ResponseBody
    public ResponseEntity removeNotification(@RequestBody MemberForm memberForm, @PathVariable("title") String title,
                                       @PathVariable("nickname") String builderNick, Model model) {
        String memberNickname = memberForm.getMemberNickname();
        if (memberNickname == null) {
            return ResponseEntity.badRequest().build();
        }

        notificationService.removeTagNotification(memberNickname, title, builderNick); // 태그 삭제 시
        return ResponseEntity.ok().build();
    }

    @PostMapping("/settings/remove/member")
    public String removeMember(@PathVariable("title") String title, @PathVariable("nickname") String nickname, String removeMember,
                               @CurrentUser Account account, Model model) {

        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);
        if (project == null) {
            return "project/settings";
        }
        projectService.removeMember(project.getId(), removeMember);

        return "project/project-main";
    }

    @PostMapping("/settings/progress")
    public String setProgress(@PathVariable("title") String title, @PathVariable("nickname") String nickname,
                              @Valid @ModelAttribute ProgressForm progressForm, Errors errors, @CurrentUser Account account, Model model) {
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);

        if (errors.hasErrors()) {
            model.addAttribute("error", "0 ~ 100의 값만 입력하세요.");
            return "redirect:/project/" + nickname + "/" + project.getEncodedTitle() + "/settings/";
        }
        Integer progress = Integer.parseInt(progressForm.getProgress());
        projectService.updateProgress(title, nickname, progress);
        return "redirect:/project/" + nickname + "/" + project.getEncodedTitle() + "/settings/";
    }

}
