package com.samika.quiz_api.web.admin.dto;

import jakarta.validation.constraints.*;
import java.time.*;

public class TournamentDtos {
    public static class CreateRequest {
        @NotBlank public String name;
        @NotBlank public String category;        // e.g., "9"
        @NotBlank public String difficulty;      // easy|medium|hard
        @FutureOrPresent public LocalDateTime startDate;
        @Future public LocalDateTime endDate;
        @Min(0) @Max(100) public Integer minPassingPercent = 0;
    }
    public static class UpdateRequest {
        @NotBlank public String name;
        public LocalDateTime startDate, endDate;
    }
}
