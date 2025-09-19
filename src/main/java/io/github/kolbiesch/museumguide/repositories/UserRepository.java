package io.github.kolbiesch.museumguide.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.kolbiesch.museumguide.entities.User;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsernameOrEmail(String username, String email);

    List<User> findByFirstNameContainingIgnoreCase(String firstName);
    List<User> findByLastNameContainingIgnoreCase(String lastName);
    List<User> findByFirstOrLastNameContainingIgnoreCase(String firstName, String lastName);

    @Query("SELECT COUNT(u) FROM User u")
    long countAllUsers();

    @Query("SELECT DISTINCT u FROM User u JOIN u.visits v")
    List<User> findUsersWithVisits();

    @Query("SELECT DISTINCT u FROM user u JOIN u.comments c WHERE c.isPublic = true")
    List<User> findUsersWithPublicComments();
}
