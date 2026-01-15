package io.github.kolbiesch.museumguide.controllers;

import io.github.kolbiesch.museumguide.services.ExhibitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import io.github.kolbiesch.museumguide.entities.Exhibit;

@RestController
@RequestMapping("/api/exhibits")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ExhibitController {

    private final ExhibitService exhibitService;

    /**
     * GET /api/exhibits
     * Get all exhibits with optional pagination
     */
    @GetMapping
    public ResponseEntity<List<Exhibit>> getAllExhibits(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        log.info("GET /api/exhibits - page: {}, size: {}", page, size);

        try {
            List<Exhibit> exhibits;

            if (page > 0 || size != 20) {
                PageRequest pageable = PageRequest.of(page, size);
                Page<Exhibit> exhibitPage = exhibitService.getAllExhibits(pageable);
                exhibits = exhibitPage.getContent();

                log.info("Found {} exhibits (page {} of {}, total: {}", exhibits.size(),
                        page + 1, exhibitPage.getTotalPages(), exhibitPage.getTotalElements());

                return ResponseEntity.ok(exhibits);
            }

            exhibits = exhibitService.getAllExhibits();
            log.info("Found {} total exhibits", exhibits.size());

            return ResponseEntity.ok(exhibits);
        } catch (Exception e) {
            log.error("Error fetching exhibits", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * GET /api/exhibits/{id}
     * Get exhibit having id {id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Exhibit> getExhibitById(@PathVariable Long id) {
        log.info("GET /api/exhibits/{}", id);

        try {
            return exhibitService.getExhibitByID(id)
                    .map(exhibit -> {
                        log.info("Found exhibit: {}",
                                exhibit.getName());
                        return ResponseEntity.ok(exhibit);
                    }).orElseGet(() -> {
                        log.warn("Exhibit not found with ID: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error fetching exhibit with ID: {}", id);
            return ResponseEntity.internalServerError().build();
        }
    }
}