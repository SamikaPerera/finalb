// CreateQuestionRequest.java
package com.samika.quiz_api.web.admin.dto;

import java.util.List;

public class CreateQuestionRequest {
    public Long tournamentId;
    public String text;
    public List<AnswerOptionDto> options;

    public static class AnswerOptionDto {
        public String text;
        public boolean correct;
    }
}
