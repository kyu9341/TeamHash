package kr.co.teamhash.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("app")
public class AppProperties {
    // application.properties 에 설정한 app.host 를 받아온다.
    private String host;

}
