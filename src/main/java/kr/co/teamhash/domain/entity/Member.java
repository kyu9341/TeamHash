package kr.co.teamhash.domain.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Member {
    @Id @GeneratedValue
    private Long id;

    // 참여한 프로젝트 id
    private Long projectId;

    // 해당 프로젝트에 참여한 유저 id
    private Long userId;

    private LocalDateTime joinDate;

}