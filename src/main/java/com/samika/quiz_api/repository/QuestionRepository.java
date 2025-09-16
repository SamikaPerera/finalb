package com.samika.quiz_api.repository;

import com.samika.quiz_api.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTournamentId(Long tournamentId);
}
