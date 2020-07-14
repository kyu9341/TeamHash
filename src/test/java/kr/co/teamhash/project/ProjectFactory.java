package kr.co.teamhash.project;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.repository.ProjectRepository;
import kr.co.teamhash.project.form.ProjectBuildForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectFactory {

    @Autowired
    ProjectService projectService;

    public Project createProject(String title, Account builderNick) {
        ProjectBuildForm projectBuildForm = new ProjectBuildForm();
        projectBuildForm.setTitle(title);
        projectBuildForm.setSubTitle(title + "프로젝트 입니다.");
        projectBuildForm.setBuilderNick(builderNick.getNickname());
        return projectService.saveNewProject(projectBuildForm, builderNick);
    }

}
