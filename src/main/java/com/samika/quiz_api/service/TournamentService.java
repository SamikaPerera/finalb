package com.samika.quiz_api.service;

import com.samika.quiz_api.domain.*;
import com.samika.quiz_api.integration.opentdb.*;
import com.samika.quiz_api.repository.*;
import com.samika.quiz_api.web.admin.dto.TournamentDtos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TournamentService {
    private final TournamentRepository tournaments;
    private final QuestionRepository questions;
    private final AnswerOptionRepository options;
    private final UserRepository users;
    private final OpenTdbClient openTdb;
    private final MailService mail;

    public TournamentService(TournamentRepository tournaments, QuestionRepository questions,
                             AnswerOptionRepository options, UserRepository users,
                             OpenTdbClient openTdb, MailService mail) {
        this.tournaments = tournaments; this.questions = questions; this.options = options;
        this.users = users; this.openTdb = openTdb; this.mail = mail;
    }

    @Transactional
    public Tournament create(TournamentDtos.CreateRequest req) {
        var t = new Tournament();
        t.setName(req.name); t.setCategory(req.category); t.setDifficulty(req.difficulty.toLowerCase());
        t.setStartDate(req.startDate); t.setEndDate(req.endDate); t.setMinPassingPercent(req.minPassingPercent);
        tournaments.save(t);

        var pulled = openTdb.fetch(10, req.category, req.difficulty);
        for (OpenTdbModels.Question qdto : pulled) {
            var q = new Question(); q.setTournament(t); q.setText(html(qdto.question)); questions.save(q);
            var correct = new AnswerOption(); correct.setQuestion(q); correct.setText(html(qdto.correct_answer)); correct.setCorrect(true); options.save(correct);
            for (var inc : qdto.incorrect_answers) {
                var ao = new AnswerOption(); ao.setQuestion(q); ao.setText(html(inc)); ao.setCorrect(false); options.save(ao);
            }
        }
        var to = users.findByRole(User.Role.PLAYER).stream().map(User::getEmail).toList();
        mail.notifyUsers(to,"New Tournament: "+t.getName(),"Difficulty: "+t.getDifficulty());
        return t;
    }

    public List<Tournament> list() { return tournaments.findAll(); }

    @Transactional
    public Tournament update(Long id, TournamentDtos.UpdateRequest req) {
        var t = tournaments.findById(id).orElseThrow();
        t.setName(req.name);
        if (req.startDate!=null) t.setStartDate(req.startDate);
        if (req.endDate!=null) t.setEndDate(req.endDate);
        return t;
    }

    public void delete(Long id) { tournaments.deleteById(id); }

    private static String html(String s){ return s==null?null:s.replace("&quot;","\"").replace("&#039;","'")
            .replace("&amp;","&").replace("&lt;","<").replace("&gt;",">"); }
}
