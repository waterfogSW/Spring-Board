package com.waterfogsw.board.board.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public record BoardCreateRequest(
    @NotBlank
    @Length(max = 20)
    String title,
    @NotBlank
    @Length(max = 200)
    String description
) {

}
