package kr.co.teamhash.project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.co.teamhash.domain.entity.Member;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.repository.MemberRepository;
import kr.co.teamhash.domain.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;

import kr.co.teamhash.domain.entity.Account;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    // 프로젝트 저장
    public Project saveNewProject(@ModelAttribute @Valid ProjectBuildForm projectBuildForm,Account account){
        
        Project project = Project.builder()
                .title(projectBuildForm.getTitle())
                .subtitle(projectBuildForm.getSubTitle())
                .builder(account.getId())
                .buildDate(LocalDateTime.now())
                .build();

        // 프로젝트 저장
        project = projectRepository.save(project);

        // Project DB와 일대다 관계의 Member DB
        // 프로젝트 생성 시 프로젝트를 생성한 사람의 id를 Member DB에 저장
        // 이후에 초대되는 유저들을 Member에 Project id와 함께 저장한다.        
        Member member = Member.builder()
                .joinDate(LocalDateTime.now())
                .projectId(project.getId())
                .userId(project.getBuilder())
                .build();

        // 멤버 저장
        // 이후 프로젝트 생성 페이지에서 유저를 검색해서 등록할 수 있게 변경
        // 유저를 검색해서 등록을 한다고 바로 맴버에 등록이 되는것이 아닌 
        // 초대 메세지를 보내는 형식으로 하는것이 좋을 듯
        memberRepository.save(member);

        return project;
    }

    @Transactional
    public List<Project> getProjectList(Account account){

        // Member DB에서 사용자가 참여중인 프로젝트 리스트찾기
        List<Member> projects = memberRepository.findAllByUserId(account.getId());
        List<Project> projectList = new ArrayList<>();


        for(Member member : projects){
            // 참여한 프로젝트가 없다면
            if(projectRepository.findById(member.getProjectId()).isEmpty())
                break;
            Project project = projectRepository.findById(member.getProjectId()).get();
            
            projectList.add(project);
        }


        return projectList;
    }
}