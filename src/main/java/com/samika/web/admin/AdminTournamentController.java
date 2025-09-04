package com.samika.quiz_api.web.admin;

import com.samika.quiz_api.domain.Tournament;
import com.samika.quiz_api.service.TournamentService;
import com.samika.quiz_api.web.dto.TournamentDtos;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/tournaments")
public class AdminTournamentController {
    private final TournamentService service;
    public AdminTournamentController(TournamentService service) { this.service = service; }

    @PostMapping public Tournament create(@Valid @RequestBody TournamentDtos.CreateRequest req){ return service.create(req); }
    @GetMapping  public List<Tournament> list(){ return service.list(); }
    @PutMapping("/{id}") public Tournament update(@PathVariable Long id, @Valid @RequestBody TournamentDtos.UpdateRequest req){ return service.update(id, req); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ service.delete(id); }
}
