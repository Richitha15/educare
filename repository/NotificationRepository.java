package com.educare.repository;

import com.educare.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserUserId(Long userId);
    List<Notification> findByUserUserIdAndStatus(Long userId, Notification.NotificationStatus status);
    List<Notification> findByUserUserIdAndCategory(Long userId, Notification.NotificationCategory category);
    long countByUserUserIdAndStatus(Long userId, Notification.NotificationStatus status);
}
