package kr.co.teamhash.domain.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamhash.domain.entity.Schedule;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
    List<Schedule> findByProjectId(Long projectId);
}