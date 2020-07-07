package kr.co.teamhash.domain.repository;

import kr.co.teamhash.domain.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamhash.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{

    void deleteAllByProblem(Problem problem);
}