package com.samika.quiz_api.web.admin;

import com.samika.quiz_api.domain.AnswerOption;
import com.samika.quiz_api.domain.Question;
import com.samika.quiz_api.domain.Tournament;
import com.samika.quiz_api.repository.QuestionRepository;
import com.samika.quiz_api.repository.TournamentRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/questions")
public class AdminQuestionController {

    private final TournamentRepository tournamentRepository;
    private final QuestionRepository questionRepository;

    public AdminQuestionController(TournamentRepository tournamentRepository,
                                   QuestionRepository questionRepository) {
        this.tournamentRepository = tournamentRepository;
        this.questionRepository = questionRepository;
    }

    // -------- CREATE --------
    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody CreateQuestionRequest req) {
        Tournament t = tournamentRepository.findById(req.tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("Tournament not found: " + req.tournamentId));

        Question q = new Question();
        q.setText(req.text);
        q.setTournament(t);

        if (req.options != null) {
            for (CreateQuestionRequest.AnswerOptionDto o : req.options) {
                AnswerOption ao = new AnswerOption();
                ao.setText(o.text);
                ao.setCorrect(o.correct);
                ao.setQuestion(q);
                q.getAnswerOptions().add(ao);
            }
        }

        Question saved = questionRepository.save(q);
        return ResponseEntity.created(URI.create("/api/admin/questions/" + saved.getId())).body(saved.getId());
    }


    @GetMapping
    public ResponseEntity<List<Question>> listByTournament(@RequestParam("tournamentId") Long tournamentId) {
        tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("Tournament not found: " + tournamentId));


        List<Question> items = questionRepository.findByTournamentId(tournamentId);
        return ResponseEntity.ok(items);
    }

    // -------- DELETE --------
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!questionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        questionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // -------- DTO --------
    public record CreateQuestionRequest(
            Long tournamentId,
            String text,
            List<AnswerOptionDto> options
    ) {
        public record AnswerOptionDto(String text, boolean correct) { }
    }
}
