package com.samika.quiz_api.web.admin;

import com.samika.quiz_api.domain.Tournament;
import com.samika.quiz_api.repository.TournamentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tournaments")
public class AdminTournamentController {

    private final TournamentRepository tournamentRepository;

    public AdminTournamentController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @GetMapping
    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }

    @PostMapping
    public Tournament create(@RequestBody Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @PutMapping("/{id}")
    public Tournament update(@PathVariable Long id, @RequestBody Tournament updated) {
        return tournamentRepository.findById(id)
                .map(t -> {
                    t.setName(updated.getName());
                    t.setCategory(updated.getCategory());
                    t.setDifficulty(updated.getDifficulty());
                    t.setStartDate(updated.getStartDate());
                    t.setEndDate(updated.getEndDate());
                    t.setMinPassingPercent(updated.getMinPassingPercent());
                    return tournamentRepository.save(t);
                })
                .orElseThrow(() -> new RuntimeException("Tournament not found"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tournamentRepository.deleteById(id);
    }
}
