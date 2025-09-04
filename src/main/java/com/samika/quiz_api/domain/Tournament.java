package com.samika.quiz_api.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Tournament {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name;
    @Column(nullable=false) private String category;      // OpenTDB category id or text
    @Column(nullable=false) private String difficulty;    // easy|medium|hard
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer minPassingPercent;
    private Boolean archived = false;
    @Column(nullable=false) private Instant createdAt;

    @OneToMany(mappedBy="tournament", cascade=CascadeType.ALL, orphanRemoval=true)
    @ToString.Exclude
    private List<Question> questions = new ArrayList<>();

    @PrePersist void pre() { if (createdAt==null) createdAt = Instant.now(); }
}
