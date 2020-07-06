package kr.co.teamhash.project.calendar;

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
    private final ProjectRepository projectRepository;

    //스케줄 저장
    public void saveNewSchedule(Schedule schedule, Account account, Long projectId ){
        
        // null data check
        if(schedule.getTitle().length() < 1 || schedule.getDate().length() < 1 || schedule.getColor().length() < 1){
            System.out.println("no data");
        }
        else{
            Project project = projectRepository.findById(projectId).get();
            schedule.setProject(project);
            schedule.getProject().getSchedules().add(schedule);
            
            scheduleRepository.save(schedule);
        }

    }

    //스케줄 삭제
    public void deleteSchedule(Long scheduleId){
        scheduleRepository.deleteById(scheduleId);
    }

}