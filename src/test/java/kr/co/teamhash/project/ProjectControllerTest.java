package kr.co.teamhash.project;

import kr.co.teamhash.WithAccount;
import kr.co.teamhash.account.AccountFactory;
import kr.co.teamhash.account.AccountService;
import kr.co.teamhash.domain.entity.*;
import kr.co.teamhash.domain.repository.CommentRepository;
import kr.co.teamhash.domain.repository.MemberRepository;
import kr.co.teamhash.domain.repository.ProblemsRepository;
import kr.co.teamhash.domain.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProjectFactory projectFactory;

    @Autowired
    AccountFactory accountFactory;

    @Autowired
    ProblemFactory problemFactory;

    @Autowired
    AccountService accountService;

    @Autowired
    CommentFactory commentFactory;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProblemsRepository problemsRepository;

    @Autowired
    CommentRepository commentRepository;

    @WithAccount("test")
    @BeforeEach
    void beforeEach() {
        String projectTitle = "testProject";
        Account test = accountService.getAccountByNickname("test");
        Project testProject = projectFactory.createProject(projectTitle, test);
        List<ProjectMember> memberList = memberRepository.findAllByAccountId(testProject.getId());
        testProject.setMembers(memberList);
        // project.getMembers() 접근 안되는 문제 - memberList 직접 넣어 해결
    }

    @WithAccount("test")
    @DisplayName("프로젝트 메인 화면")
    @Test
    void projectMain() throws Exception {
        String projectTitle = "testProject";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");

        mockMvc.perform(get("/project/test/" + testProject.getEncodedTitle() + "/main"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/project-main"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attributeExists("members"))
                .andExpect(model().attributeExists("isMember"));
    }

    @WithAccount("test")
    @DisplayName("프로젝트 설명 작성 화면")
    @Test
    void projectDescription() throws Exception {
        String projectTitle = "testProject";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");

        mockMvc.perform(get("/project/test/" + testProject.getEncodedTitle() + "/main/write"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/write"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attributeExists("isMember"));
    }

    @WithAccount("test")
    @DisplayName("프로젝트 설명 작성 - 입력값 정상")
    @Test
    void writeDescription() throws Exception {
        String projectTitle = "testProject";
        String projectDescription = "프로젝트 설명입니다~~~~~~";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");

        mockMvc.perform(post("/project/test/" + testProject.getEncodedTitle() + "/main/write")
                .param("description", projectDescription)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/test/" + testProject.getEncodedTitle() + "/main"));

        assertEquals(testProject.getDescription(), projectDescription);

    }

    @WithAccount("test")
    @DisplayName("문제 공유란 화면")
    @Test
    void problemShare() throws Exception {
        String projectTitle = "testProject";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");

        mockMvc.perform(get("/project/test/" + testProject.getEncodedTitle() + "/problem-share"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("project/problem-share"))
                        .andExpect(model().attributeExists("account"))
                        .andExpect(model().attributeExists("project"))
                        .andExpect(model().attributeExists("isMember"))
                        .andExpect(model().attributeExists("problemList"))
                        .andExpect(model().attributeExists("nowTime"));
    }

    @WithAccount("test")
    @DisplayName("문제 공유란 화면 : post 작성 - 입력값 정상")
    @Test
    void problemShare_post() throws Exception {
        String projectTitle = "testProject";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");

        mockMvc.perform(post("/project/test/" + testProject.getEncodedTitle() + "/problem-share/post")
                .param("title", "게시글 제목 입니다.")
                .param("content", "게시글 내용 입니다.")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/test/" + testProject.getEncodedTitle() + "/problem-share"));
        List<Problem> problems = problemsRepository.findByProjectId(testProject.getId());
        assertNotNull(problems);
    }

    @WithAccount("test")
    @DisplayName("문제 공유란 화면 : post 작성 - 입력값 에러")
    @Test
    void problemShare_post_with_wrong_input() throws Exception {
        String projectTitle = "testProject";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");

        mockMvc.perform(post("/project/test/" + testProject.getEncodedTitle() + "/problem-share/post")
                .param("title", "제목")
                .param("content", "내용")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/test/" + testProject.getEncodedTitle() + "/problem-share"));
        List<Problem> problems = problemsRepository.findByProjectId(testProject.getId());
        assertEquals(problems.size(), 0);
    }


    @WithAccount("test")
    @DisplayName("문제 공유란 화면 : comment 작성 - 입력값 정상")
    @Test
    void problemShare_comment() throws Exception {
        String projectTitle = "testProject";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");
        Account test = accountService.getAccountByNickname("test");
        Problem problem = problemFactory.createProblem("문제공유 제목", test, testProject.getId());
        problem.setComments(new ArrayList<Comment>());
        List<Problem> problems = problemsRepository.findByProjectId(testProject.getId());
        assertNotNull(problems);

        mockMvc.perform(post("/project/test/" + testProject.getEncodedTitle() + "/problem-share/comment")
                .param("problemId", problem.getId().toString())
                .param("content", "댓글 내용 입니다.")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/test/" + testProject.getEncodedTitle() + "/problem-share"));

        List<Comment> commentList = commentRepository.findAllByProblem(problem);
        assertEquals(commentList.size(), 1);
        assertEquals(problem.getComments().size(), 1);

    }


    @WithAccount("test")
    @DisplayName("문제 공유란 화면 : post 삭제")
    @Test
    void problemShare_post_delete() throws Exception {
        String projectTitle = "testProject";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");
        Account test = accountService.getAccountByNickname("test");
        Problem problem = problemFactory.createProblem("문제공유 제목", test, testProject.getId());
        problem.setComments(new ArrayList<Comment>());
        commentFactory.createComment("댓글1111", test, problem.getId().toString());

        mockMvc.perform(delete("/project/test/" + testProject.getEncodedTitle() + "/problem-share/" + problem.getId())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/test/" + testProject.getEncodedTitle() + "/problem-share"));
        List<Problem> problems = problemsRepository.findByProjectId(testProject.getId());
        assertEquals(problems.size(), 0);
    }

    @WithAccount("test")
    @DisplayName("문제 공유란 화면 : comment 삭제")
    @Test
    void problemShare_comment_delete() throws Exception {
        String projectTitle = "testProject";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");
        Account test = accountService.getAccountByNickname("test");
        Problem problem = problemFactory.createProblem("문제공유 제목", test, testProject.getId());
        problem.setComments(new ArrayList<Comment>());
        Comment comment1 = commentFactory.createComment("댓글 내용..", test, problem.getId().toString());
        commentFactory.createComment("댓글 내용22..", test, problem.getId().toString());

        assertEquals(problem.getComments().size(), 2);

        mockMvc.perform(delete("/project/test/" + testProject.getEncodedTitle() + "/problem-share/comment/" + comment1.getId())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/test/" + testProject.getEncodedTitle() + "/problem-share"));

        List<Comment> comments = problemsRepository.findById(problem.getId()).get().getComments();
        assertEquals(comments.size(), 1);
    }

    @WithAccount("test")
    @DisplayName("프로젝트 설정 화면")
    @Test
    void settings() throws Exception {
        String projectTitle = "testProject";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");

        mockMvc.perform(get("/project/test/" + testProject.getEncodedTitle() + "/settings"))
               .andExpect(status().isOk())
               .andExpect(view().name("project/settings"))
               .andExpect(model().attributeExists("project"))
               .andExpect(model().attributeExists("members"))
               .andExpect(model().attributeExists("whitelist"))
               .andExpect(model().attributeExists("progressForm"))
               .andExpect(model().attributeExists("isMember"))
               .andExpect(model().attributeExists("sentInvitations"));
    }



}