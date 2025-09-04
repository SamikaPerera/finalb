package com.samika.quiz_api.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="tournament_id")
    @ToString.Exclude
    private Tournament tournament;

    @Column(nullable=false, columnDefinition="TEXT") private String text;

    @OneToMany(mappedBy="question", cascade=CascadeType.ALL, orphanRemoval=true)
    @ToString.Exclude
    private List<AnswerOption> options = new ArrayList<>();
}
