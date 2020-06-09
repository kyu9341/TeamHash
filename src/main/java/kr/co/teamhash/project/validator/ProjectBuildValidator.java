package kr.co.teamhash.project.validator;

import kr.co.teamhash.project.form.ProjectBuildForm;
import lombok.RequiredArgsConstructor;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.repository.ProjectRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectBuildValidator implements Validator{
    private final ProjectRepository projectRepository;

    @Override
    public boolean supports(Class<?> clazz){
        return clazz.isAssignableFrom(ProjectBuildForm.class);
    }


    @Override
    public void validate(Object target, Errors errors){
        ProjectBuildForm projectBuildForm = (ProjectBuildForm)target;
        log.info("result = " + projectBuildForm.getTitle() + ", " + projectBuildForm.getBuilderNick());
        List<Project> projectListByBuilderNick = projectRepository.findAllByBuilderNick(projectBuildForm.getBuilderNick());

        for (Project project : projectListByBuilderNick) {
            if(project.getTitle().equals(projectBuildForm.getTitle())){
                errors.rejectValue("title", "invalid.title",
                        new Object[]{projectBuildForm.getTitle()}, "이미 사용중인 프로젝트명 입니다.");
            }
        }
    }
}