package io.github.kolbiesch.museumguide.controllers;

import io.github.kolbiesch.museumguide.entities.Comment;
import io.github.kolbiesch.museumguide.services.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CreateCommentRequest request) {
        log.info("POST /api/comments - userId: {}, exhibitId: {}", request.getUserId(), request.getExhibitId());

        try {
            Comment comment = commentService.createComment(
                    request.getUserId(),
                    request.getExhibitId(),
                    request.getContent()
            );

            CommentResponse response = CommentResponse.fromComment(comment);
            log.info("Comment created successfully with id: {}", comment.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid comment request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error creating comment", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCommentRequest {
        @NotNull(message = "User ID is required")
        private Long userId;

        @NotNull(message = "Exhibit ID is required")
        private long exhibitId;

        @NotBlank(message = "Comment content cannot be empty")
        private String content;

        private Boolean isPublic = true;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class CommentResponse {
        private Long id;
        private Long userId;
        private String username;
        private Long exhibitId;
        private String exhibitName;
        private String content;
        private Boolean isPublic;
        private LocalDateTime createdAt;

        public static  CommentResponse fromComment(Comment comment) {
            return CommentResponse.builder()
                    .id(comment.getId())
                    .userId(comment.getUser().getId())
                    .username(comment.getUser().getUsername())
                    .exhibitId(comment.getExhibit().getId())
                    .exhibitName(comment.getExhibit().getName())
                    .content(comment.getContent())
                    .isPublic(comment.getIsPublic())
                    .createdAt(comment.getCreatedAt())
                    .build();
        }
    }
}
