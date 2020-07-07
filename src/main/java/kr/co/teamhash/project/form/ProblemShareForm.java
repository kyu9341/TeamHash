package kr.co.teamhash.project.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ProblemShareForm {
    @NotBlank
    @Size(min = 3)
    private String title;

    @NotBlank
    @Size(min = 3)
    private String content;
}