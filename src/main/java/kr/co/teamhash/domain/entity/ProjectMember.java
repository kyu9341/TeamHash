package kr.co.teamhash.domain.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class ProjectMember {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private LocalDateTime joinDate;

}