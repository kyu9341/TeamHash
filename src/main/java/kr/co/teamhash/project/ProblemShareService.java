package kr.co.teamhash.project;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Problems;
import kr.co.teamhash.domain.repository.ProblemsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemShareService {
    private final ProblemsRepository problemsRepository;


    // 문제 공유 글 저장
    @Transactional
    public Long saveProblem(Problems problem, Long projectId,Account account){

        problem.setProjectId(projectId);
        problem.setWriterId(account);
    
        return problemsRepository.save(problem).getId();
    }

    // 문제 공유 글 리스트 얻기
    @Transactional
    public List<Problems> getProblemList(Long projectId){

        List<Problems> problemList = problemsRepository.findByProjectId(projectId);

        return problemList;
    }

    // 문제 공유 글 내용 얻기
    @Transactional
    public Problems getProblem(Long problemId){
        Problems problem = problemsRepository.findById(problemId).get();

        return problem;
    }

    // 문제 공유 글 삭제
    @Transactional
    public void deleteProblem(Long problemId){
        problemsRepository.deleteById(problemId);
    }
}