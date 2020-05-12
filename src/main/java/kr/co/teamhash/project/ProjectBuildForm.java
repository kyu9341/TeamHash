package kr.co.teamhash.project;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ProjectBuildForm {

    @NotBlank
    private String title;

    @NotBlank
    private String subTitle;
}