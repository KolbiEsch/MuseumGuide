package io.github.kolbiesch.museumguide.services;

import io.github.kolbiesch.museumguide.entities.Exhibit;
import io.github.kolbiesch.museumguide.repositories.ExhibitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ExhibitServiceTest {

    @Autowired
    private ExhibitService exhibitService;

    @Autowired
    private ExhibitRepository exhibitRepository;

    private Exhibit exhibit1;
    private Exhibit exhibit2;

    @BeforeEach
    void setUp() {
        exhibitRepository.deleteAll();

        exhibit1 = new Exhibit();
        exhibit1.setName("Ancient Egypt");
        exhibit1.setLocation("Hall A");
        exhibit1.setIsActive(true);

        exhibit2 = new Exhibit();
        exhibit2.setName("Roman Empire");
        exhibit2.setLocation("Hall B");
        exhibit2.setIsActive(false);

        exhibitRepository.save(exhibit1);
        exhibitRepository.save(exhibit2);
    }

    @Test
    @DisplayName("Should find an exhibit by its ID")
    void getExhibitByID_shouldReturnExhibit() {
        // ACT: Call the service method we want to get
        Optional<Exhibit> foundExhibit = exhibitService.getExhibitByID(exhibit1.getId());

        // ASSERT: Check that the result is what we expect
        assertThat(foundExhibit).isPresent();
        assertThat(foundExhibit.get().getName()).isEqualTo("Ancient Egypt");
    }

    @Test
    @DisplayName("Should return empty optional for non-existent exhibit ID")
    void getExhibitByID_shouldReturnEmptyForNonExistentID() {
        // ACT
        Optional<Exhibit> foundExhibit = exhibitService.getExhibitByID(Long.MAX_VALUE);

        // ASSERT
        assertThat(foundExhibit).isNotPresent();
    }

    @Test
    @DisplayName("Should return only active exhibits")
    void getActiveExhibits_shouldReturnOnlyActive() {
        // ACT
        List<Exhibit> activeExhibits = exhibitService.getActiveExhibits();

        // ASSERT
        assertThat(activeExhibits).hasSize(1);
        assertThat(activeExhibits.get(0).getName()).isEqualTo("Ancient Egypt");
        assertThat(activeExhibits.get(0).getIsActive()).isTrue();
    }

    @Test
    @DisplayName("Should return all exhibits when searching with a null keyword")
    void searchExhibits_withNullKeyword_shouldReturnAll() {
        // ACT
        List<Exhibit> searchResults = exhibitService.searchExhibits(null);

        // ASSERT
        assertThat(searchResults).hasSize(2);
        assertThat(searchResults).contains(exhibit1, exhibit2);
    }

}
