package com.challengerstory.chattingstory.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker // ws message borker 사용 설정 on(stomp 기반 ws 사용시 필수)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*
     @EnableWebSocketMessageBroker를 설정하면
     내부적으로 DelegatingWebSocketMessageBrokerConfiguration 이라는 설정 클래스를 가져옴
     WebSocketMessageBrokerConfigurer를 구현한 빈이 있다면 거기 있는 오버라이드 메서드로 설정 위임
     if) 구현 x == 위임 x == ws 설정이 필요없다고 생각해서 아무것도 안한다. (ws 연결 자체가 실패한다.)
    */

     @Override
     public void registerStompEndpoints(StompEndpointRegistry registry) {
          registry.addEndpoint("/ws")
                  .setAllowedOrigins("http://localhost:5173")
                  .withSockJS();
     }

     @Override
     public void configureMessageBroker(MessageBrokerRegistry registry) {
          registry.enableSimpleBroker("/topic");
          // pub/sub을 위한 topic
          // server -> client에게 /topic을 구독하고 있는 사람들에게 broadcast
          // 쉽게 말해서 서버가 메시지를 보내는 채널(broadcast 대상)
          // @SendTo("/topic/messages") -> 클라이언트가 /topic/messages 구독하면 수신 가능

          registry.setApplicationDestinationPrefixes("/app");
          // 클라이언트에서 메시지를 보낼때의 prefix
          // client -> server로 메시지를 보낼 때 "/app" 이라는 perfix를 달고 보내면 ws 요청으로 적용
          // 클라이언트가 서버에 메시지를 보낼 때 사용하는 prefix
          // 클라이언트가 /app/send로 ws 요청을 보내면 @MessageMapping("/send")에서 처리
     }

     @Override
     public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
          // ws 전송 설정(버퍼 크기, 전송 시간, 메시지 크기) 등을 커스터마이징 가능
          registry.setMessageSizeLimit(8192)
                  .setSendTimeLimit(15 * 1000)
                  .setSendBufferSizeLimit(3 * 512 * 1024);
     }

     /*
      * 4. void configureClientInboundChannel(ChannelRegistration registration)
      * 클라 -> 서버 방향 메시지를 전달하는 채널 설정 (클라이언트 메시지 받기 전 검증하는 인터셉터 설정)
      * 메시지 수신 전에 인증검사 or 로깅 가능
      *
      * 5. void configureClientOutboundChannel(ChannelRegistration registration)
      * 서버 -> 클라로 방향 메시지를 전달하는 채널 설정
      * 브로드캐스트 후 로깅 등
      *
      * 6. void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
      *   @MessageMapping 핸들러 메서드에서 사용할 커스텀 파라미터 리졸버 등록
      *   -> jwt에서 사용자 정보 뽑아서 @Payload로 넘기고 싶을때
      *
      * 7. void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers)
      *  @MessageMapping 결과값을 가공할 수 있는 리턴 핸들러 등록
      * -> 메시지 전송 전 응답 데이터 변경하고 싶을때
      * */

}
