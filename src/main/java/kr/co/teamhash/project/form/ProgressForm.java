package kr.co.teamhash.project.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ProgressForm {

    @NotBlank
    @Size(min = 1, max = 3)
    @Pattern(regexp = "^[\\d]{1,3}$") // 정규 표현식으로 패턴 지정
    private String progress;

}

