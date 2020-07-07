package kr.co.teamhash.project.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CommentForm {
    @NotBlank
    String content;

    @NotBlank
    String problemId;
}
