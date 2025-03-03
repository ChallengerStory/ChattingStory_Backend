package com.challengerstory.chattingstory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // client가 /topic/.. 으로 시작하는 주제를 구독
        registry.enableSimpleBroker("/topic");

        // client가 /app/.. 으로 시작하는 엔드포인트로 메시지를 전송할 수 있음
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws")
                .setAllowedOrigins(frontendUrl)
                .withSockJS();
    }
}
