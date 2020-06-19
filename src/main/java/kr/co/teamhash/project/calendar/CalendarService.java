package kr.co.teamhash.project.calendar;

import kr.co.teamhash.domain.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import kr.co.teamhash.domain.entity.Notification;
import kr.co.teamhash.domain.entity.ProjectMember;
import kr.co.teamhash.domain.repository.AccountRepository;
import kr.co.teamhash.domain.repository.NotificationRepository;
import kr.co.teamhash.notification.NotificationType;
import kr.co.teamhash.project.form.ProjectBuildForm;
import org.springframework.stereotype.Service;

import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.repository.MemberRepository;
import kr.co.teamhash.domain.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

import kr.co.teamhash.domain.entity.Account;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarService {
    
    private final ScheduleRepository scheduleRepository;

    public void saveNewSchedule(){
        
    }

}