package io.github.kolbiesch.museumguide.controllers;

import io.github.kolbiesch.museumguide.entities.Visit;
import io.github.kolbiesch.museumguide.services.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class VisitController {

    private final VisitService visitService;

    /**
     * POST /api/visits
     * Log a new visit
     */
    @PostMapping
    public ResponseEntity<VisitResponse> createVisit(@Valid @RequestBody CreateVisitRequest request) {
        log.info("POST /api/visits - userId: {}, exhibitId: {}", request.getUserId(), request.getExhibitId());

        try {
            Visit visit = visitService.createVisit(
                    request.getUserId(),
                    request.getExhibitId(),
                    request.getVisitDate(),
                    request.getDurationMinutes(),
                    request.getRating(),
                    request.getNotes()
            );

            VisitResponse response = VisitResponse.fromVisit(visit);
            log.info("Visit created successfully with id: {}", visit.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid visit request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error creating visit", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class CreateVisitRequest {
        @jakarta.validation.constraints.NotNull(message = "User ID is required")
        private Long userId;

        @jakarta.validation.constraints.NotNull(message = "Exhibit ID is required")
        private Long exhibitId;

        private LocalDateTime visitDate;

        @jakarta.validation.constraints.Min(value = 1, message = "Duration must be at least 1 minute")
        private Integer durationMinutes;

        private String notes;

        @jakarta.validation.constraints.Min(value = 1, message = "Rating must be between 1 and 5")
        @jakarta.validation.constraints.Max(value = 5, message = "Rating must be between 1 and 5")
        private Integer rating;
    }

    @lombok.Data
    @lombok.Builder
    @lombok.AllArgsConstructor
    public static class VisitResponse {
        private Long id;
        private Long userId;
        private String username;
        private Long exhibitId;
        private String exhibitName;
        private LocalDateTime visitDate;
        private Integer durationMinutes;
        private Integer rating;
        private String notes;
        private LocalDateTime createdAt;

        public static VisitResponse fromVisit(Visit visit) {
            return VisitResponse.builder()
                    .id(visit.getId())
                    .userId(visit.getUser().getId())
                    .username(visit.getUser().getUsername())
                    .exhibitId(visit.getExhibit().getId())
                    .exhibitName(visit.getExhibit().getName())
                    .visitDate(visit.getVisitDate())
                    .durationMinutes(visit.getDurationMinutes())
                    .rating(visit.getRating())
                    .notes(visit.getNotes())
                    .createdAt(visit.getCreatedAt())
                    .build();
        }
    }
}