package kr.co.teamhash.project;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequiredArgsConstructor
public class ProjectController {

    @GetMapping("/build-project")
    public String buildProject(){
        return "project/buildProject";
    }


}
