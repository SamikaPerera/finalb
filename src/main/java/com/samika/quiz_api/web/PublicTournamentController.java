package com.samika.quiz_api.web;

import com.samika.quiz_api.domain.AnswerOption;
import com.samika.quiz_api.domain.Question;
import com.samika.quiz_api.domain.Tournament;
import com.samika.quiz_api.repository.QuestionRepository;
import com.samika.quiz_api.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class PublicTournamentController {

    private final TournamentRepository tournaments;
    private final QuestionRepository questions;

    @GetMapping
    public List<Tournament> list() {
        return tournaments.findAll();
    }

    @GetMapping("/{id}/questions")
    public List<Question> questions(@PathVariable Long id) {
        // naive guard: ensure tournament exists
        tournaments.findById(id).orElseThrow();
        // returns questions with options (JPA lazy -> rely on jackson to fetch)
        return questions.findAll().stream()
                .filter(q -> q.getTournament() != null && q.getTournament().getId().equals(id))
                .toList();
    }
}
