package kr.co.teamhash.domain.entity;



import lombok.*;

@Getter @Setter  @AllArgsConstructor @NoArgsConstructor
public class ScheduleDTO {

    private Long id;
    
    private String date;

    private String title;

    private String content;

    private String color;

}