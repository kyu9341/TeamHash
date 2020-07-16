package kr.co.teamhash.domain.repository;

import kr.co.teamhash.domain.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamhash.domain.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>{

    void deleteAllByProblem(Problem problem);

    List<Comment> findAllByProblem(Problem problem);
}