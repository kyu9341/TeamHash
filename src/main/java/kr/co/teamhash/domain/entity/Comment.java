package kr.co.teamhash.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name ="problems_id")
    private Problems problemId;

    @ManyToOne
    @JoinColumn(name ="account_id")
    private Account writerId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

}