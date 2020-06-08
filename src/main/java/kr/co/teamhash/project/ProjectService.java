package kr.co.teamhash.project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import kr.co.teamhash.domain.entity.ProjectMember;
import org.springframework.stereotype.Service;

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
    public Project saveNewProject(ProjectBuildForm projectBuildForm, Account account){

        Project project = Project.builder()
                .title(projectBuildForm.getTitle())
                .subtitle(projectBuildForm.getSubTitle())
                .builder(account.getId())
                .builderNick(projectBuildForm.getBuilderNick())
                .buildDate(LocalDateTime.now())
                .build();

        // 프로젝트 저장
        project = projectRepository.save(project);

        // Project DB와 일대다 관계의 Member DB
        // 프로젝트 생성 시 프로젝트를 생성한 사람의 id를 Member DB에 저장
        // 이후에 초대되는 유저들을 Member에 Project id와 함께 저장한다.        
        ProjectMember projectMember = ProjectMember.builder()
                .joinDate(LocalDateTime.now())
                .project(project)
                .account(account)
                .build();

        // 멤버 저장
        // 이후 프로젝트 생성 페이지에서 유저를 검색해서 등록할 수 있게 변경
        // 유저를 검색해서 등록을 한다고 바로 맴버에 등록이 되는것이 아닌 
        // 초대 메세지를 보내는 형식으로 하는것이 좋을 듯
        memberRepository.save(projectMember);

        return project;
    }


    //사용자의 프로젝트 리스트 찾기
    @Transactional
    public List<Project> getProjectList(Account account){

        // Member DB에서 사용자가 참여중인 프로젝트 리스트찾기
        List<ProjectMember> projects = memberRepository.findAllByAccountId(account.getId());
        List<Project> projectList = new ArrayList<>();


        for(ProjectMember projectMember : projects){
            // 참여한 프로젝트가 없다면
            if(!projectRepository.findById(projectMember.getProject().getId()).isPresent()){
                break;
            }


            Project project = projectRepository.findById(projectMember.getProject().getId()).get();
            
            projectList.add(project);
        }


        return projectList;
    }


    // nickname과 projectTitle로 projectId 찾기
    @Transactional
    public Long getProjectId(String nickname, String title){
        List<Project> projectList =  projectRepository.findAllByBuilderNick(nickname);

        for (Project project : projectList) {
            if(project.getTitle().equals(title))
                return project.getId();
        }

        return null;
    }

    // 프로젝트 아이디로 프로젝트 찾기
    // 프로젝트 아이디로 페이지에 접근할 때 해당 url에서 projectId를 추출해서 사용
    @Transactional
    public Optional<Project> getProject(Long projectId){
        return projectRepository.findById(projectId);
    }

    // 해당 프로젝트에 포함된 맴버리스트 반환
    @Transactional
    public List<ProjectMember> getMemberList(Long projectId){

        List<ProjectMember> projectMemberList = memberRepository.findAllByProjectId(projectId);

        return projectMemberList;

    }

    // 해당 유저의 프로젝트 소속 여부 확인
    @Transactional
    public boolean isMember(Long projectId, Account account){
        
        boolean im = false;
        List<ProjectMember> projectMemberList = memberRepository.findAllByProjectId(projectId);

        for(ProjectMember projectMember : projectMemberList){
            if(projectMember.getAccount().getId() == account.getId()){
                im = true;
                break;
            }
        }
        return im;
    }
}