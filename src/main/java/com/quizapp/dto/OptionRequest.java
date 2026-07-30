package com.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionRequest {

    @NotBlank(message = "Option text must not be blank")
    private String text;

    private boolean correct;
}
