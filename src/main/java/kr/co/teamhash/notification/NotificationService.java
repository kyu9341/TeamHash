package kr.co.teamhash.notification;

import kr.co.teamhash.account.AccountService;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Notification;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.repository.AccountRepository;
import kr.co.teamhash.domain.repository.NotificationRepository;
import kr.co.teamhash.domain.repository.ProjectRepository;
import kr.co.teamhash.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AccountService accountService;
    private final ProjectService projectService;

    public void addNotification(String invitedMember, String title, String nickname) {
        Account account = accountService.getAccountByNickname(invitedMember);
        Project project = projectService.getProject(nickname, title);
        notificationRepository.save(Notification.builder()
                .account(account)
                .project(project)
                .createdLocalDateTime(LocalDateTime.now())
                .notificationType(NotificationType.PROJECT_INVITE)
                .message("<b>" + project.getBuilderNick() + "</b>님의 <b>" + project.getTitle() + "</b>에 초대 받았습니다.")
                .build());
    }

    public void removeTagNotification(String invitedMember, String title, String nickname) {
        Account account = accountService.getAccountByNickname(invitedMember);
        Project project = projectService.getProject(nickname, title);
        notificationRepository.removeByAccountIdAndProjectId(account.getId(), project.getId());
    }

    public List<Notification> getSentInvitations(Long projectId) {
        return notificationRepository.findAllByProjectId(projectId);
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }


}
