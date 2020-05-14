package com.springboot.chatting;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // Spring Configuration class
@EnableWebSocketMessageBroker // WebScoket message handling을 허용해준다 
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override // MessageBroker는 송신자에게 수신자의 이전 메세지 프로토콜로 변환해주는 모듈 중 하나
            // 요청이 오면 그에 해당하는 통신 채널로 전송, 응답 또한 같은 경로로 가서 응답한다.
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic"); // 메세지 응답 preifx
    config.setApplicationDestinationPrefixes("/app"); // 클라이언트에서 메세지 송신 시 붙여줄 prefix
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {// 최초 소켓 연결 시 endpoint
    registry.addEndpoint("/gs-guide-websocket").withSockJS(); // javascript에서 SockJS생성자를 통해 연결
    
  
  }

}