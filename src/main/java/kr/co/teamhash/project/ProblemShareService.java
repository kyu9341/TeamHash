package kr.co.teamhash.project;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kr.co.teamhash.domain.entity.Problems;
import kr.co.teamhash.domain.repository.ProblemsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemShareService {
    private final ProblemsRepository problemsRepository;


    @Transactional
    public Long saveProblem(Problems problem, Long projectId){

        problem.setProjectId(projectId);
        return problemsRepository.save(problem).getId();
    }

    @Transactional
    public List<Problems> getProblemList(Long projectId){

        List<Problems> problemList = problemsRepository.findByProjectId(projectId);

        return problemList;
    }

    @Transactional
    public Problems getProblem(Long problemId){
        Problems problem = problemsRepository.findById(problemId).get();

        return problem;
    }
}