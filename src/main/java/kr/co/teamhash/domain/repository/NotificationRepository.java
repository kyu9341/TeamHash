package kr.co.teamhash.domain.repository;

import kr.co.teamhash.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByProjectId(Long projectId);

    Notification findByAccountIdAndProjectId(Long accountId, Long projectId);

    void removeByAccountIdAndProjectId(Long accountId, Long projectId);

    List<Notification> findAllByAccountId(Long accountId);
}
