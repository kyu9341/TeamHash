package kr.co.teamhash.project;

import kr.co.teamhash.WithAccount;
import kr.co.teamhash.account.AccountFactory;
import kr.co.teamhash.account.AccountService;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.entity.ProjectMember;
import kr.co.teamhash.domain.repository.MemberRepository;
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

    @WithAccount("test")
    @DisplayName("프로젝트 메인 화면")
    @Test
    void projectMain() throws Exception {
        String projectTitle = "project1";
        Account test = accountService.getAccountByNickname("test");
        Project project1 = projectFactory.createProject(projectTitle, test);
        List<ProjectMember> memberList = memberRepository.findAllByAccountId(project1.getId());
        project1.setMembers(memberList);

        // TODO project.getMembers() 접근 안되는 문제 - memberList 직접 넣어 해결
        mockMvc.perform(get("/project/test/" + project1.getEncodedTitle() + "/main"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/project-main"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attributeExists("members"))
                .andExpect(model().attributeExists("isMember"));
    }
}