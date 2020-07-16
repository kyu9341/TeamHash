package kr.co.teamhash.project;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Problem;
import kr.co.teamhash.project.form.ProblemShareForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProblemFactory {

    @Autowired
    ProblemShareService problemShareService;

    public Problem createProblem(String title, Account account, Long projectId) {
        ProblemShareForm problemShareForm = new ProblemShareForm();
        problemShareForm.setTitle(title);
        problemShareForm.setContent(title + "내용 입니다.~~~");
        return problemShareService.saveProblem(problemShareForm, projectId, account);
    }

}
