package kr.co.teamhash.project.calendar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.teamhash.account.CurrentUser;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.entity.Schedule;
import kr.co.teamhash.domain.entity.ScheduleDTO;
import kr.co.teamhash.project.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final ProjectService projectService;
    private final CalendarService calendarService;
    // 캘린더
    @GetMapping("/project/{nickname}/{title}/calendar")
    public String calendar(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                           Model model,  @CurrentUser Account account){
        
        // nickname과 projectTitle로 projectId 찾기
        Long projectId = projectService.getProjectId(nickname, title);
        
        // schedule DTO 변환
        Project thisProject = projectService.getProject(projectId).get();
        List<Schedule> schedules = thisProject.getSchedules();
        List<ScheduleDTO> scheduleDTO = new ArrayList<ScheduleDTO>();

        for (Schedule schedule : schedules) {
            scheduleDTO.add(new ScheduleDTO(schedule.getId(),
                                schedule.getDate(),
                                schedule.getTitle(),
                                schedule.getContent(),
                                schedule.getColor()));
        }


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
        model.addAttribute("account", account);
        model.addAttribute("schedules", scheduleDTO);

        return "project/calendar";
    }


    // 스케줄 생성
    @PostMapping("/project/{nickname}/{title}/calendar/make")
    public String makeSchedule(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                                Model model,  @CurrentUser Account account, Schedule schedule){
         


        // nickname과 projectTitle로 projectId 찾기
        Long projectId = projectService.getProjectId(nickname, title);

        calendarService.saveNewSchedule(schedule, account, projectId);
        
        System.out.println(schedule.toString());

        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);
        
        model.addAttribute("account", account);                                    
        return "redirect:/project/"+nickname+"/"+title+"/calendar";                   

    }

    // 스케줄 삭제
    @PostMapping("/project/{nickname}/{title}/calendar/delete")
    public String deleteSchedule(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                    Model model,  @CurrentUser Account account, Long scheduleId){

        System.out.println(scheduleId);
        calendarService.deleteSchedule(scheduleId);

        // nickname과 projectTitle로 projectId 찾기
        Long projectId = projectService.getProjectId(nickname, title);


        System.out.println(scheduleId);
        calendarService.deleteSchedule(scheduleId);


        model.addAttribute("projectId", projectId);
        model.addAttribute("title", title);
        
        model.addAttribute("account", account);      
        return "redirect:/project/"+nickname+"/"+title+"/calendar";     
    }
   

}