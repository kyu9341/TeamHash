package kr.co.teamhash.project;

import kr.co.teamhash.account.UserAccount;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.entity.Project;
import kr.co.teamhash.domain.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectInterceptor implements HandlerInterceptor {

    private final ProjectService projectService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (modelAndView != null && !isRedirectView(modelAndView)) {
            Account account = ((UserAccount) authentication.getPrincipal()).getAccount();
            Map<String, Object> model = modelAndView.getModel(); // 모델에서 해당 프로젝트를 가져온다.
            Project project = (Project) model.get("project");

            boolean isMember = projectService.isMember(project.getId(), account);
            modelAndView.addObject("isMember", isMember);
        }
    }


    private boolean isRedirectView(ModelAndView modelAndView) { // 리다이렉트 요청인지 확인하는 메서드
        // 뷰의 이름이 redirect: 로 시작하거나, RedirectView 타입인 경우
        return modelAndView.getViewName().startsWith("redirect:") || modelAndView.getView() instanceof RedirectView;
    }
}
