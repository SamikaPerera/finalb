package com.samika.quiz_api.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournament")
@Data                       // <-- generates getters/setters/toString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String difficulty;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer minPassingPercent;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private Boolean archived;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Question> questions = new ArrayList<>();
}
