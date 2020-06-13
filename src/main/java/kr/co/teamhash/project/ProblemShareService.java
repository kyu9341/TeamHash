package kr.co.teamhash.project;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Comment;
import kr.co.teamhash.domain.entity.Problems;
import kr.co.teamhash.domain.repository.CommentRepository;
import kr.co.teamhash.domain.repository.ProblemsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemShareService {
    private final ProblemsRepository problemsRepository;
    private final CommentRepository commentRepository;

    // 문제 공유 글 저장
    public Long saveProblem(Problems problem, Long projectId,Account account){


        //공백 값 검출
        if(problem.getTitle().length()<3)
            return null;
        else if(problem.getContent().length()<9)
            return null;

        problem.setProjectId(projectId);
        problem.setWriterId(account);
    
        return problemsRepository.save(problem).getId();
    }

    // 문제 공유 글 리스트 얻기
    public List<Problems> getProblemList(Long projectId){

        List<Problems> problemList = problemsRepository.findByProjectId(projectId);

        return problemList;
    }

    // 문제 공유 글 내용 얻기
    public Problems getProblem(Long problemId){
        Problems problem = problemsRepository.findById(problemId).get();

        return problem;
    }

    // 문제 공유 글 삭제
    public void deleteProblem(Long problemId){
        //문제 공유 글 삭제시 해당 글에 있는 
        // 코멘트를 모두 제거해야 한다.
        for (Comment comment : this.getProblem(problemId).getComments()) {
            this.deleteComment(comment.getId());
        }
        problemsRepository.deleteById(problemId);
    }


    //코멘트 작성
    public void saveComment(Comment comment, Long problemId, Account account){

        //외래키 객체 주입

        Problems problem = getProblem(problemId);
        
        //공백값 검출
        if(comment.getContent().length()<5)
            return;
        
        comment.setProblemId(problem);
        comment.getProblemId().getComments().add(comment);
        comment.setWriterId(account);

        commentRepository.save(comment);
    }

    //코멘트 삭제
    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }
}