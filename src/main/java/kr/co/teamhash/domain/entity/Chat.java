package kr.co.teamhash.domain.entity;



import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Chat {
    @Id @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private Long projectId;

    @ManyToOne
    @JoinColumn(name ="account_id")
    @Column(nullable = false)
	private Account sender;
	
    @Column(columnDefinition = "TEXT", nullable = false)
	private String message;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime sendDateTime;
}