package com.samika.quiz_api.web.admin;

import com.samika.quiz_api.domain.AnswerOption;
import com.samika.quiz_api.domain.Question;
import com.samika.quiz_api.domain.Tournament;
import com.samika.quiz_api.repository.AnswerOptionRepository;
import com.samika.quiz_api.repository.QuestionRepository;
import com.samika.quiz_api.repository.TournamentRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/questions")
@RequiredArgsConstructor
public class AdminQuestionController {

    private final TournamentRepository tournamentRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;

    // ---------- CREATE ----------
    @PostMapping
    @Transactional
    public ResponseEntity<Question> create(@RequestBody CreateQuestionRequest req) {
        Tournament t = tournamentRepository.findById(req.getTournamentId())
                .orElseThrow(() -> new IllegalArgumentException("Tournament not found: " + req.getTournamentId()));

        Question q = new Question();
        q.setText(req.getText());
        q.setTournament(t);

        // make sure list exists
        if (q.getAnswerOptions() == null) {
            q.setAnswerOptions(new ArrayList<>());
        }

        if (req.getOptions() != null) {
            for (CreateQuestionRequest.AnswerOptionDto o : req.getOptions()) {
                AnswerOption ao = new AnswerOption();
                ao.setText(o.getText());
                ao.setCorrect(o.isCorrect());
                ao.setQuestion(q);          // set FK
                q.getAnswerOptions().add(ao);
            }
        }

        Question saved = questionRepository.save(q);
        return ResponseEntity.created(URI.create("/api/admin/questions/" + saved.getId())).body(saved);
    }

    // ---------- LIST by tournament ----------
    @GetMapping
    public List<Question> listByTournament(@RequestParam("tournamentId") Long tournamentId) {
        return questionRepository.findByTournamentId(tournamentId);
    }

    // ---------- GET one ----------
    @GetMapping("/{id}")
    public ResponseEntity<Question> getOne(@PathVariable Long id) {
        return questionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ---------- DELETE ----------
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!questionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        // delete options first if FK constraints
        answerOptionRepository.deleteByQuestionId(id);
        questionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Request DTO ----------
    @Data
    public static class CreateQuestionRequest {
        private Long tournamentId;
        private String text;
        private List<AnswerOptionDto> options;

        @Data
        public static class AnswerOptionDto {
            private String text;
            private boolean correct;
        }
    }
}
