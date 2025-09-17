// src/main/java/com/samika/quiz_api/repository/QuestionRepository.java
package com.samika.quiz_api.repository;

import com.samika.quiz_api.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Add this method
    List<Question> findByTournamentId(Long tournamentId);
}
