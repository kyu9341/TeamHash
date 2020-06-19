package kr.co.teamhash.config;

import kr.co.teamhash.notification.NotificationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
//@EnableWebMvc : 이 설정을 주면 스프링 부트가 제공하는 웹mvc 자동설정을 사용하지 않는다는 뜻.
// 지금은 추가설정만 하는 것
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final NotificationInterceptor notificationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> staticResourcesPath = Arrays.stream(StaticResourceLocation.values())
                .flatMap(StaticResourceLocation::getPatterns)
                .collect(Collectors.toList());
        staticResourcesPath.add("/node_modules/**"); // 커스텀하게 추가했던 부분 추가로 넣어줌

        registry.addInterceptor(notificationInterceptor)
                .excludePathPatterns(staticResourcesPath); // 인터셉터를 적용하지 않을 url 패턴
    }
}
