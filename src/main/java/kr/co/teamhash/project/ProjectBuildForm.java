package kr.co.teamhash.project;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ProjectBuildForm {

    @Size(min = 3, max = 20)
    @NotBlank
    private String title;

    private String subTitle;

    private String builderNick;
}