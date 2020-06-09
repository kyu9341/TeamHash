package kr.co.teamhash.domain.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamhash.domain.entity.Chat;


public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByProjectId(Long projectId);
}