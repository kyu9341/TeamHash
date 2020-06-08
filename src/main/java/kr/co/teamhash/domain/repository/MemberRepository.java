package kr.co.teamhash.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamhash.domain.entity.ProjectMember;



public interface MemberRepository extends JpaRepository<ProjectMember, Long> {

    List<ProjectMember> findAllByAccountId(Long accountId);

    
    List<ProjectMember> findAllByProjectId(Long projectId);
}