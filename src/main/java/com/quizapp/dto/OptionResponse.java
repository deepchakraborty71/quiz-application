package com.quizapp.dto;

import com.quizapp.entity.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Deliberately omits the "correct" flag so a client taking the quiz
 * can never see the answer key in the response payload.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionResponse {

    private Long id;
    private String text;

    public static OptionResponse from(Option option) {
        return new OptionResponse(option.getId(), option.getText());
    }
}
