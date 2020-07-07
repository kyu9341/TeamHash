package kr.co.teamhash.project.calendar;

import kr.co.teamhash.domain.entity.Problem;
import kr.co.teamhash.domain.repository.ProjectRepository;
import kr.co.teamhash.domain.repository.ScheduleRepository;



import javax.transaction.Transactional;

import kr.co.teamhash.domain.entity.Schedule;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Project;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {
    
    private final ScheduleRepository scheduleRepository;

    //스케줄 저장
    public void saveNewSchedule(ScheduleForm scheduleForm, Project project){
        Schedule schedule = Schedule.builder()
                    .project(project)
                    .date(scheduleForm.getDate())
                    .title(scheduleForm.getTitle())
                    .content(scheduleForm.getContent())
                    .color(scheduleForm.getColor())
                    .build();
        project.addSchedule(schedule);
        scheduleRepository.save(schedule);
    }

    //스케줄 삭제
    public void deleteSchedule(Long scheduleId){
        scheduleRepository.deleteById(scheduleId);
    }

}