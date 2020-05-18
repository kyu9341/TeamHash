package kr.co.teamhash.project;

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
    public Long saveProblem(Problems problem){

        return problemsRepository.save(problem).getId();
    }
}