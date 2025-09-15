package com.samika.quiz_api.web.admin;

import com.samika.quiz_api.domain.Tournament;
import com.samika.quiz_api.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/admin/tournaments")
@RequiredArgsConstructor
public class AdminTournamentController {

    private final TournamentRepository tournamentRepository;

    @GetMapping
    public List<Tournament> list() {
        return tournamentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> get(@PathVariable Long id) {
        return tournamentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tournament> create(@RequestBody Tournament in) {
        in.setId(null);
        in.setCreatedAt(Instant.now());
        Tournament saved = tournamentRepository.save(in);
        return ResponseEntity.created(URI.create("/api/admin/tournaments/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tournament> update(@PathVariable Long id, @RequestBody Tournament in) {
        return tournamentRepository.findById(id)
                .map(existing -> {
                    existing.setName(in.getName());
                    existing.setCategory(in.getCategory());
                    existing.setDifficulty(in.getDifficulty());
                    existing.setStartDate(in.getStartDate());
                    existing.setEndDate(in.getEndDate());
                    existing.setMinPassingPercent(in.getMinPassingPercent());
                    existing.setArchived(in.getArchived());
                    Tournament saved = tournamentRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!tournamentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tournamentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
