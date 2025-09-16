package com.samika.quiz_api.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    // ✅ add this
    @OneToMany(mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AnswerOption> answerOptions = new ArrayList<>();

    // --- helpers (optional but handy)
    public void addOption(AnswerOption ao) {
        answerOptions.add(ao);
        ao.setQuestion(this);
    }
    public void removeOption(AnswerOption ao) {
        answerOptions.remove(ao);
        ao.setQuestion(null);
    }

    // getters/setters
    public Long getId() { return id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Tournament getTournament() { return tournament; }
    public void setTournament(Tournament tournament) { this.tournament = tournament; }

    public List<AnswerOption> getAnswerOptions() { return answerOptions; }
    public void setAnswerOptions(List<AnswerOption> answerOptions) { this.answerOptions = answerOptions; }
}
