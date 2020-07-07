package kr.co.teamhash.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamhash.domain.entity.Problem;

public interface ProblemsRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByTitleContaining(String keyword);
    List<Problem> findByProjectId(Long projectId);

}
