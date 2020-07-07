package kr.co.teamhash.domain.entity;


import lombok.*;

import javax.persistence.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 프로젝트 관리 DB
@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Project {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String subtitle;

    private String builderNick;

    //프로젝트를 생성한 유저의 id를 넣을 것
    private Long builder;

    private Integer progress = 0;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String description;

    @OneToMany(mappedBy = "project")
    private List<ProjectMember> members = new ArrayList<>();

    // @OneToMany(mappedBy = "project", targetEntity=Schedule.class)
    @OneToMany(mappedBy = "project")
    private List<Schedule> schedules = new ArrayList<>();

    private LocalDateTime buildDate;

    public String getEncodedTitle() {
        return URLEncoder.encode(this.title, StandardCharsets.UTF_8);
    }

    public boolean checkMember (Account account) {
        for (ProjectMember projectMember : this.getMembers()) {
            if (projectMember.getAccount().equals(account)) {
                return true;
            }
        }
        return false;
    }

    public void addSchedule (Schedule schedule) {
        this.getSchedules().add(schedule);
    }
}