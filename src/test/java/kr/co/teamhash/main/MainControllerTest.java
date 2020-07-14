package kr.co.teamhash.main;

import kr.co.teamhash.WithAccount;
import kr.co.teamhash.account.AccountService;
import kr.co.teamhash.account.SignUpForm;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.entity.ProjectMember;
import kr.co.teamhash.domain.repository.AccountRepository;
import kr.co.teamhash.domain.repository.MemberRepository;
import kr.co.teamhash.domain.repository.ProjectRepository;
import kr.co.teamhash.project.ProjectService;
import kr.co.teamhash.project.form.ProjectBuildForm;
import lombok.With;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    AccountService accountService;

    @Autowired
    ProjectService projectService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach(){
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("teamhash");
        signUpForm.setEmail("teamhash@email.com");
        signUpForm.setPassword("123456");
        accountService.processNewAccount(signUpForm);
    }

    @DisplayName("로그인 성공")
    @Test
    void login_success() throws Exception{
        mockMvc.perform(post("/login")
                .param("username", "teamhash@email.com")
                .param("password", "123456")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"))
                .andExpect(authenticated().withUsername("teamhash"));
        // teamhash 이란 이름으로 인증이 될 것이다. because UserAccount 에서 username 을 nickname 으로 리턴하였기 때문

    }

    @DisplayName("로그인 실패")
    @Test
    void login_fail() throws Exception {
        mockMvc.perform(post("/login")
                .param("username","11111111111")
                .param("password","0000000000")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @WithMockUser
    @DisplayName("로그아웃")
    @Test
    void logout() throws Exception {
        mockMvc.perform(post("/logout")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }


    @WithAccount("test")
    @DisplayName("메인 화면 - 프로젝트가 없는 경우")
    @Test
    void main() throws Exception {
        mockMvc.perform(get("/main"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attributeExists("projectBuildForm"))
                .andExpect(model().attributeDoesNotExist("projectList"))
                .andExpect(model().attributeDoesNotExist("schedules"))
                .andExpect(model().attributeExists("account"))
                .andExpect(authenticated().withUsername("test"));
    }

    @WithAccount("test")
    @DisplayName("메인 화면 - 프로젝트 생성 - 입력값 정상")
    @Test
    void createProject() throws Exception {
        String projectTitle = "testProject";
        mockMvc.perform(post("/main")
                .param("title", "testProject")
                .param("subTitle", "testSubTitle")
                .param("builderNick", "test")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"))
                .andExpect(authenticated().withUsername("test"));

        Project project = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");
        assertNotNull(project);
        Account account = accountRepository.findByNickname("test");
        assertNotNull(account);
        ProjectMember projectMember = memberRepository.findByAccountId(account.getId());
        assertEquals(projectMember.getAccount(), account);
        assertEquals(projectMember.getProject(), project);
    }


    @WithAccount("test")
    @DisplayName("메인 화면 - 프로젝트 생성 - 입력값 에러 : 중복된 프로젝트명")
    @Test
    void createProject_with_wrong_input() throws Exception {
        String projectTitle = "testProject";
        Account account = accountRepository.findByNickname("test");
        ProjectBuildForm projectBuildForm = new ProjectBuildForm();
        projectBuildForm.setTitle(projectTitle);
        projectBuildForm.setSubTitle("testSubTitle");
        projectBuildForm.setBuilderNick("test");
        projectService.saveNewProject(projectBuildForm, account);

        mockMvc.perform(post("/main")
                .param("title", "testProject")
                .param("subTitle", "testSubTitle")
                .param("builderNick", "test")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("projectBuildForm"))
                .andExpect(authenticated().withUsername("test"));
        // TODO projectList check

    }

}