package kr.co.teamhash.domain.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamhash.domain.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

  
}