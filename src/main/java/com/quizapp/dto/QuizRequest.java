package com.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequest {

    @NotBlank(message = "Quiz title must not be blank")
    private String title;

    private String description;
}
