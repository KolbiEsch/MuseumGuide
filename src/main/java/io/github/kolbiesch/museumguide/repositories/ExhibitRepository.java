package io.github.kolbiesch.museumguide.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import io.github.kolbiesch.museumguide.entities.Exhibit;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {

    List<Exhibit> findByIsActiveTrue();
    List<Exhibit> findByIsActiveFalse();
    Optional<Exhibit> findByNameIgnoreCase(String name);
    Optional<Exhibit> findById(Long id);

    List<Exhibit> findByNameContainingIgnoreCase(String name);
    List<Exhibit> findByDescriptionContainingIgnoreCase(String description);

    List<Exhibit> findByLocation(String location);
    List<Exhibit> findByLocationContainingIgnoreCase(String location);

    List<Exhibit> findByStartDateAfter(LocalDateTime date);
    List<Exhibit> findByEndDateBefore(LocalDateTime date);

    @Query("SELECT e FROM Exhibit e WHERE e.name LIKE %:keyword% OR e.description LIKE %:keyword%")
    List<Exhibit> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT e FROM Exhibit e WHERE e.startDate <= :currentDate AND (e.endDate IS NULL OR e.endDate >= :currentDate)")
    List<Exhibit> findCurrentExhibits(@Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT e FROM Exhibit e WHERE e.startDate > :currentDate")
    List<Exhibit> findUpcomingExhibits(@Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT e FROM Exhibit e WHERE e.endDate < :currentDate")
    List<Exhibit> findPastExhibits(@Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT e FROM Exhibit e WHERE e.isActive = true AND e.startDate <= :currentDate AND (e.endDate IS NULL OR e.endDate >= :currentDate)")
    List<Exhibit> findActiveCurrentExhibits(@Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT e FROM Exhibit e JOIN e.visits v GROUP BY e ORDER BY COUNT(v) DESC")
    List<Exhibit> findMostPopularExhibits();

    @Query("SELECT e FROM Exhibit e JOIN e.visits v GROUP BY e HAVING COUNT(v) >= :minVisits ORDER BY COUNT(v) DESC")
    List<Exhibit> findExhibitsWithMinimumVisits(@Param("minVisits") long minVisits);

    @Query("SELECT e FROM Exhibit e JOIN e.visits v WHERE v.rating IS NOT NULL GROUP BY e ORDER BY AVG(v.rating) DESC")
    List<Exhibit> findTopRatedExhibits();

    @Query("SELECT e FROM Exhibit e JOIN e.visits v WHERE v.rating IS NOT NULL GROUP BY e HAVING AVG(v.rating) >= :minRating ORDER BY AVG(v.rating) DESC")
    List<Exhibit> findExhibitsWithMinRating(@Param("minRating") double minRating);
}
