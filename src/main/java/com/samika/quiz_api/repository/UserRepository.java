package com.samika.quiz_api.repository;

import com.samika.quiz_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(User.Role role);
}
