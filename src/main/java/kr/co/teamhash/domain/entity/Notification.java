package kr.co.teamhash.domain.entity;

import kr.co.teamhash.notification.NotificationType;
import lombok.*;

import javax.accessibility.AccessibleIcon;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Builder
@Getter @Setter @EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id @GeneratedValue
    private Long id;

    private String message;

    private boolean checked;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private LocalDateTime createdLocalDateTime;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

}
