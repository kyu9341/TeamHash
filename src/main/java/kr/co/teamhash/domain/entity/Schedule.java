package kr.co.teamhash.domain.entity;



import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Schedule {

    @Id @GeneratedValue
    private Long id;


    // 프로젝트에 적용된 스케줄
    // 이후 유저 -> 프로젝트맴버 -> 프로젝트 -> 스케쥴로 접근하여 
    // 메인 페이지에 유저의 스케줄 표시해줄 것    
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String date;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String color;

}