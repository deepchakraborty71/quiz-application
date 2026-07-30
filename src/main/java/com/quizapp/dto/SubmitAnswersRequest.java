package com.quizapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitAnswersRequest {

    /** Map of questionId -> chosen optionId */
    @NotEmpty(message = "At least one answer must be submitted")
    private Map<Long, Long> answers;
}
