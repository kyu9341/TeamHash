package com.springboot.hibernate.repository;

import com.springboot.hibernate.vo.MemberVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberVo, Long> {
     // 비워있어도 잘 작동함.
    // long 이 아니라 Long으로 작성. ex) int => Integer 같이 primitive형식 사용못함
    // findBy뒤에 컬럼명을 붙여주면 이를 이용한 검색이 가능하다
    public List<MemberVo> findById(String id);

    public List<MemberVo> findByName(String name);

    // like검색도 가능
    public List<MemberVo> findByNameLike(String keyword);
}
