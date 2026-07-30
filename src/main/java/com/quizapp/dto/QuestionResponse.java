package com.quizapp.dto;

import com.quizapp.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {

    private Long id;
    private String text;
    private List<OptionResponse> options;

    public static QuestionResponse from(Question question) {
        List<OptionResponse> options = question.getOptions().stream()
                .map(OptionResponse::from)
                .toList();
        return new QuestionResponse(question.getId(), question.getText(), options);
    }
}
