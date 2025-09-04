package com.samika.quiz_api.integration.opentdb;
import java.util.List;

public class OpenTdbModels {
    public static class Response { public int response_code; public List<Question> results; }
    public static class Question {
        public String category, type, difficulty, question, correct_answer;
        public List<String> incorrect_answers;
    }
}
