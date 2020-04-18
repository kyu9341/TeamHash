package com.springboot.jpaboard.domain.repo;

import com.springboot.jpaboard.domain.entity.Board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
}
