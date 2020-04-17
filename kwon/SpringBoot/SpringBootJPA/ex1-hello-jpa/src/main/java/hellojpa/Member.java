package hellojpa;

import javax.persistence.*;

@Entity
// @Table(name = "USER") // 테이블 직접 설정 가능
public class Member {
// 자동으로 같은 이름을 가진 테이블로 매핑됨
    @Id
    private Long id;

    // @Column(name = "username") // 직접 컬럼명 지정 가능
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
