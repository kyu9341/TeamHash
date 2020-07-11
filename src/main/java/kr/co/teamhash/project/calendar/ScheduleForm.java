package kr.co.teamhash.project.calendar;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ScheduleForm {
    
    private Long scheduleId;

    @NotBlank
    @Size(min = 1)
    private String date;

    @NotBlank
    @Size(min = 1)
    private String title;

    private String content;

    @NotBlank
    private String color;
}
