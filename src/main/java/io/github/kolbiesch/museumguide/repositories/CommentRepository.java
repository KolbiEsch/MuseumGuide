package io.github.kolbiesch.museumguide.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.github.kolbiesch.museumguide.entities.Comment;
import io.github.kolbiesch.museumguide.entities.User;
import io.github.kolbiesch.museumguide.entities.Exhibit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUser(User user);
    List<Comment> findByUserId(Long userId);

    List<Comment> findByExhibit(Exhibit exhibit);
    List<Comment> findByExhibitId(Long exhibitId);

    List<Comment> findByIsPublicTrue();
    List<Comment> findByIsPublicFalse();
    List<Comment> findByExhibitIdAndIsPublicTrue(Long exhibitId);

    List<Comment> findByContentContainingIgnoreCase(String keyword);

    List<Comment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Comment> findByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT c FROM Comment c WHERE c.exhibit.id = :exhibitId AND c.content LIKE %:keyword% AND c.isPublic = true")
    List<Comment> searchPublicCommentByExhibitAndKeyword(@Param("exhibitId") Long exhibitId, @Param("keyword") String keyword);

    @Query("SELECT c FROM Comment c WHERE c.isPublic = true ORDER BY c.createdAt DESC")
    List<Comment> findRecentPublicComments();

    @Query("SELECT c FROM Comment c WHERE c.exhibit.id = :exhibitId AND c.isPublic = true ORDER BY c.createdAt DESC")
    List<Comment> findRecentPublicCommentsByExhibit(@Param("exhibitId") Long exhibitId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.user.id = :userId")
    long countCommentsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.exhibit.id = :exhibitId AND c.isPublic = true")
    long countPublicCommentsByExhibitId(@Param("exhibitId") Long exhibitId);
}
