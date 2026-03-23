package com.educare.repository;

import com.educare.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByFromUserUserId(Long fromUserId);
    List<Message> findByToUserUserId(Long toUserId);

    @Query("SELECT m FROM Message m WHERE (m.fromUser.userId = :userId OR m.toUser.userId = :userId) AND m.status != 'DELETED' ORDER BY m.sentAt DESC")
    List<Message> findAllMessagesForUser(@Param("userId") Long userId);

    @Query("SELECT m FROM Message m WHERE m.fromUser.userId = :userId1 AND m.toUser.userId = :userId2 OR m.fromUser.userId = :userId2 AND m.toUser.userId = :userId1 ORDER BY m.sentAt ASC")
    List<Message> findConversation(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}
