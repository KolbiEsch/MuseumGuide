package io.github.kolbiesch.museumguide.services;

import io.github.kolbiesch.museumguide.entities.Comment;
import io.github.kolbiesch.museumguide.entities.Exhibit;
import io.github.kolbiesch.museumguide.entities.User;
import io.github.kolbiesch.museumguide.repositories.CommentRepository;
import io.github.kolbiesch.museumguide.repositories.ExhibitRepository;
import io.github.kolbiesch.museumguide.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ExhibitRepository exhibitRepository;

    @Transactional
    public Comment createComment(Long userId, Long exhibitId, String content) {
        log.info("Creating comment for user {} on exhibit {}", userId, exhibitId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Exhibit exhibit = exhibitRepository.findById(exhibitId)
                .orElseThrow(() -> new IllegalArgumentException("Exhibit not found with id: " + exhibitId));

        Comment comment = Comment.builder()
                .user(user)
                .exhibit(exhibit)
                .content(content).build();

        Comment savedComment = commentRepository.save(comment);
        log.info("Comment created successfully with id: {}", savedComment.getId());

        return savedComment;
    }

    public List<Comment> getCommentByExhibitId(Long Id) {
        log.info("Fetching visits by Exhibit with ID: {}", Id);
        return commentRepository.findByExhibitId(Id);
    }
}
