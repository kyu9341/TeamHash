package kr.co.teamhash.domain.repository;

import kr.co.teamhash.domain.entity.Notification;
import kr.co.teamhash.domain.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByProjectId(Long projectId);

}
