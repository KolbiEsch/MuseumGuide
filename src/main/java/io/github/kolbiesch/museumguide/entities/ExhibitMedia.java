package io.github.kolbiesch.museumguide.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "exhibit_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"exhibit"})
public class ExhibitMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibit_id", nullable = false)
    private Exhibit exhibit;

    @Column(name = "media_type", nullable = false, length = 50)
    private MediaType mediaType;

    @Column(name = "media_url", nullable = false, length = 500)
    private String mediaUrl;

    @Column(length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Builder.Default
    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Media type enum
    public enum MediaType {
        IMAGE("image"),
        VIDEO("video"),
        AUDIO("audio"),
        VIRTUAL_TOUR_360("360_tour"),
        DOCUMENT("document"),
        MODEL_3D("3d_model");

        private final String value;

        MediaType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @JsonCreator
        public static MediaType fromValue(String value) {
            if (value == null) {
                return null;
            }

            for (MediaType mediaType : values()) {
                if (mediaType.value.equalsIgnoreCase(value)) {
                    return mediaType;
                }
            }

            throw new IllegalArgumentException("Unknown enum type " + value);
        }
    }
}
