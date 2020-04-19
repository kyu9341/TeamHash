package com.springboot.jpaboard.domain.repo;

import java.util.List;

import com.springboot.jpaboard.domain.entity.Board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContaining(String keyword);
}
