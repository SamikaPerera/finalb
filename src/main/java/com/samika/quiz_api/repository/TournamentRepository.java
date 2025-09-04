package com.samika.quiz_api.repository;
import com.samika.quiz_api.domain.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TournamentRepository extends JpaRepository<Tournament, Long> {}
