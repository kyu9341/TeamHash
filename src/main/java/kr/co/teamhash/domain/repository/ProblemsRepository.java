package kr.co.teamhash.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamhash.domain.entity.Problems;

public interface ProblemsRepository extends JpaRepository<Problems, Long> {
    List<Problems> findByTitleContaining(String keyword);
    List<Problems> findByProjectId(Long projectId);

}
