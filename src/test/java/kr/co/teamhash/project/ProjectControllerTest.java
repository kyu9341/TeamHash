package kr.co.teamhash.project;

import kr.co.teamhash.WithAccount;
import kr.co.teamhash.account.AccountFactory;
import kr.co.teamhash.account.AccountService;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Problem;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.entity.ProjectMember;
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

import java.util.List;

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
    AccountService accountService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProblemsRepository problemsRepository;

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

}