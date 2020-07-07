package kr.co.teamhash.project.calendar;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.teamhash.domain.repository.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;
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
@RequestMapping("/project/{nickname}/{title}")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final ProjectRepository projectRepository;

    // 캘린더
    @GetMapping("/calendar")
    public String calendar(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                           Model model,  @CurrentUser Account account){

        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);

        // nickname과 projectTitle에 맞는 프로젝트가 없을 때
        if(project == null)
            return "project/no-project";

        // schedule DTO 변환
        List<Schedule> schedules = project.getSchedules();
        List<ScheduleDTO> scheduleDTO = new ArrayList<ScheduleDTO>();

        for (Schedule schedule : schedules) {
            scheduleDTO.add(new ScheduleDTO(schedule.getId(),
                                schedule.getDate(),
                                schedule.getTitle(),
                                schedule.getContent(),
                                schedule.getColor()));
        }

        // 프로젝트에 필요한 정보와
        // 유저가 해당 프로젝트의 맴버인지 확인하는 정보
        model.addAttribute(project);
        model.addAttribute("title", title);
        model.addAttribute(account);
        model.addAttribute("schedules", scheduleDTO);

        return "project/calendar";
    }

    // 스케줄 생성
    @PostMapping("/calendar/make")
    public String makeSchedule(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                                Model model,  @CurrentUser Account account, @Valid ScheduleForm scheduleForm, Errors errors){
        
        // nickname과 projectTitle로 projectId 찾기
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);

        if (errors.hasErrors()) {
            model.addAttribute("error", "최소 입력 길이를 만족시켜 주세요");
            return "redirect:/project/" + nickname + "/" + project.getEncodedTitle() + "/calendar";
        }
       
        calendarService.saveNewSchedule(scheduleForm, project);
        return "redirect:/project/" + nickname + "/" + project.getEncodedTitle() + "/calendar";

    }

    // 스케줄 삭제
    @PostMapping("/calendar/delete")
    @ResponseBody
    public ResponseEntity deleteSchedule(@PathVariable("nickname") String nickname, @PathVariable("title") String title,
                                         Model model, @CurrentUser Account account, @RequestBody ScheduleForm scheduleForm) {
        Long scheduleId = scheduleForm.getScheduleId();
        log.info("scheduleId : " + scheduleId);
        // nickname과 projectTitle로 projectId 찾기
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);

        if (project == null) {
            return ResponseEntity.badRequest().build();
        }

        System.out.println(scheduleId);
        calendarService.deleteSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }
   

}