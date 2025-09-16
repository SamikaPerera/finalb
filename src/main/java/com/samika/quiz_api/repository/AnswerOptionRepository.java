package com.samika.quiz_api.repository;

import com.samika.quiz_api.domain.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {

    @Modifying
    @Query("delete from AnswerOption ao where ao.question.id = :questionId")
    void deleteByQuestionId(Long questionId);
}
