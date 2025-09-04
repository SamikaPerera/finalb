package com.samika.quiz_api.repository;
import com.samika.quiz_api.domain.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {}
