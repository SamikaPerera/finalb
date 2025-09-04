package com.samika.quiz_api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AnswerOption {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="question_id")
    @ToString.Exclude
    private Question question;

    @Column(nullable=false, columnDefinition="TEXT") private String text;
    @Column(nullable=false) private boolean correct;
}
