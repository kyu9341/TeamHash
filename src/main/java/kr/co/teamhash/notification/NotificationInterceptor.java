package kr.co.teamhash.notification;

import kr.co.teamhash.account.UserAccount;
import kr.co.teamhash.domain.entity.Account;
import kr.co.teamhash.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class NotificationInterceptor implements HandlerInterceptor {

    private final NotificationRepository notificationRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 뷰 렌더링 전, 핸들러 처리 이후

        // 인증 정보를 가지고 있는 요청에만 처리를 해주기 위해 인증 객체를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // modelAndView 를 사용하고, RedirectView 가 아닌 경우, 인증 정보가 있고 principal 이 UserAccount 타입일 때 적용
        if (modelAndView != null && !isRedirectView(modelAndView) && authentication != null && authentication.getPrincipal() instanceof UserAccount) {
            Account account = ((UserAccount)authentication.getPrincipal()).getAccount(); // principal 안에 들어있는 객체를 UserAccount 로 형변환하여  account 객체를 가져옴
            long count = notificationRepository.countByAccount(account); // 알림의 개수를 구한다.
            modelAndView.addObject("hasNotification", count > 0); // 알람이 있는 경우 true, 없으면 false
        }
    }

    private boolean isRedirectView(ModelAndView modelAndView) { // 리다이렉트 요청인지 확인하는 메서드
        // 뷰의 이름이 redirect: 로 시작하거나, RedirectView 타입인 경우
        return modelAndView.getViewName().startsWith("redirect:") || modelAndView.getView() instanceof RedirectView;
    }
}
