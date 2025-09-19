package io.github.kolbiesch.museumguide.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.kolbiesch.museumguide.entities.Visit;
import io.github.kolbiesch.museumguide.entities.User;
import io.github.kolbiesch.museumguide.entities.Exhibit;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findByUser(User user);
    List<Visit> findByUserId(Long userId);

    List<Visit> findByExhibit(Exhibit exhibit);
    List<Visit> findByExhibitId(Long exhibitId);

    List<Visit> findByUserAndExhibit(User user, Exhibit exhibit);
    Optional<Visit> findFirstByUserIdAndExhibitIdOrderByVisitDateDesc(Long userId, Long exhibitId);

    List<Visit> findByVisitDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Visit> findByVisitDateAfter(LocalDateTime date);
    List<Visit> findByVisitDateBefore(LocalDateTime date);

    List<Visit> findByRating(Integer rating);
    List<Visit> findByRatingGreaterThanEqual(Integer minRating);
    List<Visit> findByRatingBetween(Integer minRating, Integer maxRating);

    List<Visit> findByDurationMinutesGreaterThan(Integer minutes);
    List<Visit> findByDurationMinutesBetween(Integer minDuration, Integer maxDuration);

    // Statistics queries
    @Query("SELECT COUNT(v) FROM Visit v WHERE v.user.id = :userId")
    long countVisitsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(v) FROM Visit v WHERE v.exhibit.id = :exhibitId")
    long countVisitsByExhibitId(@Param("exhibitId") Long exhibitId);

    @Query("SELECT AVG(v.durationMinutes) FROM Visit v WHERE v.exhibit.id = :exhibitId AND v.durationMinutes IS NOT NULL")
    Optional<Double> findAverageDurationByExhibitId(@Param("exhibitId") Long exhibitId);
}
