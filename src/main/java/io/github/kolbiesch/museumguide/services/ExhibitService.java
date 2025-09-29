package io.github.kolbiesch.museumguide.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.github.kolbiesch.museumguide.repositories.ExhibitRepository;
import io.github.kolbiesch.museumguide.entities.Exhibit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ExhibitService {

    private final ExhibitRepository exhibitRepository;

    public Page<Exhibit> getAllExhibits(Pageable pageable) {
        log.debug("Fetching all exhibits with pagination: {}", pageable);
        return exhibitRepository.findAll(pageable);
    }

    public List<Exhibit> getAllExhibits() {
        log.debug("Fetching all exhibits");
        return exhibitRepository.findAll();
    }

    public Optional<Exhibit> getExhibitByID(long id) {
        log.debug("Fetching exhibit {}", id);
        return exhibitRepository.findById(id);
    }

    public List<Exhibit> getActiveExhibits() {
        log.debug("Fetching active exhibits");
        return exhibitRepository.findByIsActiveTrue();
    }

    public List<Exhibit> getNonActiveExhibits() {
        log.debug("Fetching non-active exhibits");
        return exhibitRepository.findByIsActiveFalse();
    }

    public Optional<Exhibit> getExhibitByName(String name) {
        log.debug("Fetching exhibit by name: {}", name);
        return exhibitRepository.findByNameIgnoreCase(name);
    }

    public List<Exhibit> getExhibitByLocation(String location) {
        log.debug("Fetching exhibits by location: {}", location);
        return exhibitRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Exhibit> getExhibitByStartDateAfter(LocalDateTime date) {
        log.debug("Fetching exhibits by start date after: {}", date);
        return exhibitRepository.findByStartDateAfter(date);
    }

    public List<Exhibit> getExhibitByEndDateBefore(LocalDateTime date) {
        log.debug("Fetching exhibits by end date before: {}", date);
        return exhibitRepository.findByEndDateBefore(date);
    }

    public List<Exhibit> searchExhibits(String keyword) {
        log.debug("Searching exhibits with keyword: {}", keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllExhibits();
        }
        return exhibitRepository.searchByKeyword(keyword);
    }

    public List<Exhibit> getCurrentExhibits() {
        LocalDateTime now = LocalDateTime.now();
        log.debug("Fetching current exhibits for date: {}", now);
        return exhibitRepository.findActiveCurrentExhibits(now);
    }

    public List<Exhibit> getPastExhibits() {
        LocalDateTime now = LocalDateTime.now();
        log.debug("Fetching past exhibits for date: {}", now);
        return exhibitRepository.findPastExhibits(now);
    }

    public List<Exhibit> getUpcomingExhibits() {
        LocalDateTime now = LocalDateTime.now();
        log.debug("Fetching upcoming exhibits for date: {}", now);
        return exhibitRepository.findUpcomingExhibits(now);
    }

    public List<Exhibit> getMostPopularExhibits() {
        log.debug("Fetching most popular exhibits");
        return exhibitRepository.findMostPopularExhibits();
    }

    public List<Exhibit> getExhibitsHavingMinVisits(long minVisits) {
        log.debug("Fetching exhibits with minimum visits: {}", minVisits);
        return exhibitRepository.findExhibitsWithMinimumVisits(minVisits);
    }

    public List<Exhibit> getTopRatedExhibits() {
        log.debug("Fetching top rated exhibits");
        return exhibitRepository.findTopRatedExhibits();
    }

    public List<Exhibit> getExhibitsHavingMinRating(double minRating) {
        log.debug("Fetching exhibits with minimum rating: {}", minRating);
        return exhibitRepository.findExhibitsWithMinRating(minRating);
    }
}