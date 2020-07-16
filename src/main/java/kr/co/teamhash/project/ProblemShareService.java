package kr.co.teamhash.project;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import kr.co.teamhash.project.form.CommentForm;
import org.springframework.stereotype.Service;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Comment;
import kr.co.teamhash.domain.entity.Problem;
import kr.co.teamhash.domain.repository.CommentRepository;
import kr.co.teamhash.domain.repository.ProblemsRepository;
import kr.co.teamhash.project.form.ProblemShareForm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemShareService {
    private final ProblemsRepository problemsRepository;
    private final CommentRepository commentRepository;

    // 문제 공유 글 저장
    public Problem saveProblem(ProblemShareForm problemForm, Long projectId, Account account){

        Problem problem = Problem.builder()
                    .title(problemForm.getTitle())
                    .content(problemForm.getContent())
                    .projectId(projectId)
                    .writer(account)
                    .build();
        return problemsRepository.save(problem);
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
        if (problem.get().isWriter(currentUser))
            commentRepository.deleteAllByProblem(problem.get());
        else
            throw new IllegalArgumentException("자신이 작성한 글만 삭제할 수 있습니다.");
        problemsRepository.deleteById(problemId);

    }


    //코멘트 작성
    public void saveComment(CommentForm commentForm, Account account){

        Problem problem = getProblem(Long.parseLong(commentForm.getProblemId()));
        Comment comment = Comment.builder()
                .content(commentForm.getContent())
                .problem(problem)
                .writer(account)
                .build();
        problem.addComment(comment);
        commentRepository.save(comment);
    }

    //코멘트 삭제
    public void deleteComment(Long commentId, Account currentUser){
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.get().isWriter(currentUser))
            commentRepository.deleteById(commentId);
        else
            throw new IllegalArgumentException("자신이 작성한 글만 삭제할 수 있습니다.");
    }
}