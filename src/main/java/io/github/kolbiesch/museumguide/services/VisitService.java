package io.github.kolbiesch.museumguide.services;

import io.github.kolbiesch.museumguide.entities.Exhibit;
import io.github.kolbiesch.museumguide.entities.User;
import io.github.kolbiesch.museumguide.entities.Visit;
import io.github.kolbiesch.museumguide.repositories.ExhibitRepository;
import io.github.kolbiesch.museumguide.repositories.UserRepository;
import io.github.kolbiesch.museumguide.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VisitService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;
    private final ExhibitRepository exhibitRepository;

    @Transactional
    public Visit createVisit(Long userId, Long exhibitId, LocalDateTime visitDate,
                             Integer durationMinutes, Integer rating, String notes) {
        log.info("Creating visit for user {} to exhibit {}", userId, exhibitId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Exhibit exhibit = exhibitRepository.findById(exhibitId)
                .orElseThrow(() -> new IllegalArgumentException("Exhibit not found with id: " + exhibitId));

        if (rating != null && (rating < 1 || rating > 5)) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Visit visit = Visit.builder()
                .user(user)
                .exhibit(exhibit)
                .visitDate(visitDate != null ? visitDate : LocalDateTime.now())
                .durationMinutes(durationMinutes)
                .rating(rating)
                .notes(notes)
                .build();

        Visit savedVisit = visitRepository.save(visit);
        log.info("Visit created successfully with id: {}", savedVisit.getId());

        return savedVisit;
    }

    public List<Visit> getVisitByUser(User user) {
        log.info("Fetching visits by User: {}", user.getUsername());
        return visitRepository.findByUser(user);
    }

    public List<Visit> getVisitByUserId(Long id) {
        log.info("Fetching visits by User with ID: {}", id);
        return visitRepository.findByUserId(id);
    }

    public List<Visit> getVisitsByExhibit(Exhibit exhibit) {
        log.info("Fetching visits by Exhibit: {}", exhibit.getName());
        return visitRepository.findByExhibit(exhibit);
    }

    public List<Visit> getVisitsByExhibitId(Long id) {
        log.info("Fetching visits by Exhibit with ID: {}", id);
        return visitRepository.findByExhibitId(id);
    }

    public List<Visit> getVisitsByUserAndExhibit(User user, Exhibit exhibit) {
        log.info("Fetching visits by User {} and Exhibit {}", user.getUsername(), exhibit.getName());
        return visitRepository.findByUserAndExhibit(user, exhibit);
    }

    public List<Visit> getVisitsByDateBetween(LocalDateTime start, LocalDateTime end) {
        log.info("Fetching visits between {} and {}", start, end);
        return visitRepository.findByVisitDateBetween(start, end);
    }

    public List<Visit> getVisitsByDateAfter(LocalDateTime date) {
        log.info("Fetching visits after {}", date);
        return visitRepository.findByVisitDateAfter(date);
    }

    public List<Visit> getVisitsByDateBefore(LocalDateTime date) {
        log.info("Fetching visits before {}", date);
        return visitRepository.findByVisitDateBefore(date);
    }

    public List<Visit> getVisitsByRating(Integer rating) {
        log.info("Fetching visits with rating: {}", rating);
        return visitRepository.findByRating(rating);
    }

    public List<Visit> getVisitsByRatingGreaterOrEqual(Integer rating) {
        log.info("Fetching visits with minimum rating: {}", rating);
        return visitRepository.findByRatingGreaterThanEqual(rating);
    }

    public List<Visit> getVisitsBetweenRating(Integer minRating, Integer maxRating) {
        log.info("Fetching visits with rating between {} and {}", minRating, maxRating);
        return visitRepository.findByRatingBetween(minRating, maxRating);
    }

    public List<Visit> getVisitsByDurationMinutesGreater(Integer minutes) {
        log.info("Fetching exhibits with duration (minutes) greater than: {}", minutes);
        return visitRepository.findByDurationMinutesGreaterThan(minutes);
    }

    public List<Visit> getVisitsByDurationMinBetween(Integer minDuration, Integer maxDuration) {
        log.info("Fetching exhibits with duration (minutes) between {} and {}", minDuration, maxDuration);
        return visitRepository.findByDurationMinutesBetween(minDuration, maxDuration);
    }

    public Long getVisitCountByUserId(Long id) {
        log.info("Fetching visit count by User with ID: {}", id);
        return visitRepository.countVisitsByUserId(id);
    }

    public Long getVisitCountByExhibitId(Long id) {
        log.info("Fetching visit count by Exhibit with ID: {}", id);
        return visitRepository.countVisitsByExhibitId(id);
    }

    public Optional<Double> getVisitAvgDurationByExhibitId(Long id) {
        log.info("Fetching average visit duration by Exhibit ID: {}", id);
        return visitRepository.findAverageDurationByExhibitId(id);
    }
}