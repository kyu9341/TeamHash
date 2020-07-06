package kr.co.teamhash.project;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Comment;
import kr.co.teamhash.domain.entity.Problem;
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
    public void saveProblem(Problem problem, Long projectId, Account account){
        //공백 값 검출
        if(problem.getTitle().length() < 3)
            return;
        else if(problem.getContent().length() < 9)
            return;

        problem.setProjectId(projectId);
        problem.setWriter(account);

        problemsRepository.save(problem);
    }

    // 문제 공유 글 리스트 얻기
    public List<Problem> getProblemList(Long projectId){
        return problemsRepository.findByProjectId(projectId);
    }

    // 문제 공유 글 내용 얻기
    public Problem getProblem(Long problemId){
        return problemsRepository.findById(problemId).get();
    }

    // 문제 공유 글 삭제
    public void deleteProblem(Long problemId, Account currentUser){
        // 문제 공유 글 삭제시 해당 글에 있는 
        // 코멘트를 모두 제거해야 한다.

        Optional<Problem> problem = problemsRepository.findById(problemId);
        if (problem.get().isAuthor(currentUser)) {
            commentRepository.deleteAllByProblem(problem.get());
        }
        problemsRepository.deleteById(problemId);

    }


    //코멘트 작성
    public void saveComment(Comment comment, Long problemId, Account account){

        //외래키 객체 주입
        Problem problem = getProblem(problemId);
        
        //공백값 검출
        if(comment.getContent().length() < 5)
            return;
        
        comment.setProblem(problem);
        comment.getProblem().getComments().add(comment);
        comment.setWriter(account);

        commentRepository.save(comment);
    }

    //코멘트 삭제
    public void deleteComment(Long commentId, Account currentUser){

        if(!commentRepository.findById(commentId).get().getWriter().
                getId().equals(currentUser.getId())){

            System.out.println("not your comment");
        }else{
            System.out.println("your comment");
            commentRepository.deleteById(commentId);
        }

        
    }
}