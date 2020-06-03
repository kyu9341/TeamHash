package kr.co.teamhash.domain.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Problems {
    @Id @GeneratedValue
    private Long id;

    private Long projectId;

    // @Column(length = 10, nullable = false)
    // private String writer;

    @ManyToOne
    @JoinColumn(name ="account_id")
    private Account writerId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    //Comment의 ProblemId와 연결을 의미함
    @OneToMany(mappedBy="problemId")
    private List<Comment> comments = new ArrayList<Comment>();
	

    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


}