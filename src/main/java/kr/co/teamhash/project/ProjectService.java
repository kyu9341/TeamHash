package kr.co.teamhash.project;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import kr.co.teamhash.account.AccountService;
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
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final AccountService accountService;

    public Project getProject(String nickname, String title) {
        Project project = projectRepository.findByTitleAndBuilderNick(title, nickname);
        checkIfExistingProject(project);
        return project;
    }

    // 프로젝트 저장
    public void saveNewProject(ProjectBuildForm projectBuildForm, Account account){
        Project project = Project.builder()
                .title(projectBuildForm.getTitle())
                .subtitle(projectBuildForm.getSubTitle())
                .builder(account.getId())
                .builderNick(projectBuildForm.getBuilderNick())
                .buildDate(LocalDateTime.now())
                .build();
        project.parseTitle();
        // 프로젝트 저장
        projectRepository.save(project);
        saveProjectMember(account.getNickname(), project.getTitle(), project.getBuilderNick());
    }

    // 해당 유저의 프로젝트 소속 여부 확인
    public boolean isMember(Long projectId, Account account){
        Optional<Project> project = projectRepository.findById(projectId);
        return project.get().checkMember(account);
    }

    public void saveProjectMember(String nickname, String title, String builderNick) {
        Account account = accountService.getAccountByNickname(nickname);
        Project project = projectRepository.findByTitleAndBuilderNick(title, builderNick);
        memberRepository.save(ProjectMember.builder()
                .account(account)
                .project(project)
                .joinDate(LocalDateTime.now())
                .build());
    }

    public void createDescription(Project project, String description) {
        project.setDescription(description);
        projectRepository.save(project);
    }

    public void removeMember(Long projectId, String removeMember) {
        Account account = accountService.getAccountByNickname(removeMember);
        memberRepository.removeByAccountIdAndProjectId(account.getId(), projectId);
    }

    public void updateProgress(Project project, Integer progressPer) {
        project.setProgress(progressPer);
        projectRepository.save(project);
    }

    public void checkIfExistingProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("존재하지 않는 프로젝트입니다.");
        }
    }

    public List<ProjectMember> getMemberList(Project project) {
        return memberRepository.findAllByProjectId(project.getId());
    }
}