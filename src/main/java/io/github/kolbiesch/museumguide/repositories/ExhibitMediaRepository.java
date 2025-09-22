package io.github.kolbiesch.museumguide.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.kolbiesch.museumguide.entities.ExhibitMedia;
import io.github.kolbiesch.museumguide.entities.ExhibitMedia.MediaType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ExhibitMediaRepository extends JpaRepository<ExhibitMedia, Long> {

    List<ExhibitMedia> findByExhibitIdOrderByDisplayOrderAsc(Long exhibitId);
    List<ExhibitMedia> findByExhibitId(Long exhibitId);

    List<ExhibitMedia> findByMediaType(MediaType mediaType);
    List<ExhibitMedia> findByExhibitIdAndMediaType(Long exhibitId, MediaType mediaType);

    List<ExhibitMedia> findByIsPrimaryTrue();
    List<ExhibitMedia> findByExhibitIdAndIsPrimaryTrue(Long exhibitID);

    @Query("SELECT em FROM ExhibitMedia em WHERE em.exhibit.id = :exhibitId AND em.isPrimary = true AND em.mediaType = 'IMAGE'")
    Optional<ExhibitMedia> findPrimaryImageByExhibitId(@Param("exhibitId") long exhibitId);

    @Query("SELECT em FROM ExhibitMedia em WHERE em.exhibit.id = :exhibitId AND em.mediaType = 'IMAGE' ORDER BY em.displayOrder ASC")
    List<ExhibitMedia> findImagesByExhibitId(@Param("exhibitId") Long exhibitId);

    @Query("SELECT em FROM ExhibitMedia em WHERE em.exhibit.id = :exhibitId AND em.mediaType = 'VIDEO' ORDER BY em.displayOrder ASC")
    List<ExhibitMedia> findVideosByExhibitId(@Param("exhibitId") Long exhibitId);

    List<ExhibitMedia> findByTitleContainingIgnoreCase(String title);
    List<ExhibitMedia> findByDescriptionContainingIgnoreCase(String description);

    @Query("SELECT em FROM ExhibitMedia em WHERE em.title LIKE %:keyword% OR em.description LIKE %:keyword%")
    List<ExhibitMedia> searchByKeyword(@Param("keyword") String keyword);

    long countByExhibitId(Long exhibitId);
    long countByExhibitIdAndMediaType(Long exhibitId, MediaType mediaType);

    @Query("SELECT DISTINCT em.exhibit FROM ExhibitMedia em WHERE em.mediaType = :mediaType")
    List<Object> findExhibitsWithMediaType(@Param("mediaType") MediaType mediaType);
}
