package kr.co.teamhash.domain.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

// 프로젝트 관리 DB
@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Project {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String subtitle;

    //프로젝트를 생성한 유저의 id를 넣을 것
    private Long builder;


    private LocalDateTime buildDate;
}