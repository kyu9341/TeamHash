package kr.co.teamhash.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamhash.domain.entity.Member;



public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByUserId(Long userId);

    
    List<Member> findAllByProjectId(Long projectId);
}