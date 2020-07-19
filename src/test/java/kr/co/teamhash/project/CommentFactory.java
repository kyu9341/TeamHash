package kr.co.teamhash.project;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Comment;
import kr.co.teamhash.domain.repository.CommentRepository;
import kr.co.teamhash.project.form.CommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentFactory {

    @Autowired
    ProblemShareService problemShareService;    ;

    public Comment createComment (String content, Account account, String problemId) {
        CommentForm commentForm = new CommentForm();
        commentForm.setContent(content);
        commentForm.setProblemId(problemId);
        return problemShareService.saveComment(commentForm, account);
    }
}
