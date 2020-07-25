package kr.co.teamhash.project.calendar;

import kr.co.teamhash.WithAccount;
import kr.co.teamhash.account.AccountService;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.entity.ProjectMember;
import kr.co.teamhash.domain.entity.Schedule;
import kr.co.teamhash.domain.repository.MemberRepository;
import kr.co.teamhash.domain.repository.ProjectRepository;
import kr.co.teamhash.domain.repository.ScheduleRepository;
import kr.co.teamhash.project.ProjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class CalendarControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired AccountService accountService;
    @Autowired ProjectFactory projectFactory;
    @Autowired MemberRepository memberRepository;
    @Autowired ProjectRepository projectRepository;
    @Autowired ScheduleRepository scheduleRepository;

    @WithAccount("test")
    @BeforeEach
    void beforeEach() {
        String projectTitle = "testProject";
        Account test = accountService.getAccountByNickname("test");
        Project testProject = projectFactory.createProject(projectTitle, test);
        List<ProjectMember> memberList = memberRepository.findAllByAccountId(testProject.getId());
        List<Schedule> schedules = scheduleRepository.findByProjectId(testProject.getId());
        testProject.setMembers(memberList);
        testProject.setSchedules(schedules);
        // project.getMembers() 접근 안되는 문제 - memberList 직접 넣어 해결
    }

    @DisplayName("캘린더 화면")
    @WithAccount("test")
    @Test
    void calender () throws Exception {
        String projectTitle = "testProject";
        Project testProject = projectRepository.findByTitleAndBuilderNick(projectTitle, "test");

        mockMvc.perform(get("/project/test/" + testProject.getEncodedTitle() + "/calendar"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/calendar"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attributeExists("isMember"))
                .andExpect(model().attributeExists("schedules"));

    }

}