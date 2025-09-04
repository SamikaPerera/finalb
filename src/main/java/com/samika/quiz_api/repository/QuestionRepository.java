package com.samika.quiz_api.repository;
import com.samika.quiz_api.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
public interface QuestionRepository extends JpaRepository<Question, Long> {}
