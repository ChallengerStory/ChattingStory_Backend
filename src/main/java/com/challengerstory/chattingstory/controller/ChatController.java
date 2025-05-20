package com.challengerstory.chattingstory.controller;


import com.challengerstory.chattingstory.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
// RestController는 Controller + ResponseBody
// -> 메서드 리턴값을 json 응답 바디로 직렬화해서 http 응답으로 보내라는 뜻
// ws는 http 요청이 아니기 때문에 일반 Controller 사용
// restController를 써도 ws 동작함. -> 중간에서 인터셉트 하기 때문
// 하지만 명확하게 or 유지보수 때문이라도 분리해서 사용하기
public class ChatController {

    @MessageMapping("/send") // 클라이언트가 /app/send로 보낸 메시지를 받음
    @SendTo("/topic/messages") // 받은 메시지를 /topic/messages를 구독중인 클라이언트에게 전송
    public ChatMessage send(ChatMessage message){
        System.out.println("받은 메시지: = " + message);
        return message;
    }

    /**
     * [Vue] 클라이언트
     *     ↓ send("/app/send")
     * [Spring] 서버
     *     → @MessageMapping("/send")
     *     → @SendTo("/topic/messages")
     *     ↓
     * [Vue] 구독자 모두 → /topic/messages 수신
     */
}
