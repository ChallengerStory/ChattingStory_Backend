package com.challengerstory.chattingstory.controller;

import com.challengerstory.chattingstory.dto.Greeting;
import com.challengerstory.chattingstory.dto.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception{
        Thread.sleep(1000);
        return new Greeting(String.format("Hello, %s!", message.getName()));
    }
}
