package com.cfc.service.rabbit;

import com.cfc.dao.model.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @auther fangchen.chai ON 2018/1/24
 */
@Component
public class HelloSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

    public void neoSender(Integer i) {
        String context = "hello -["+ i+"]" + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

    //发送者
    public void send(User user) {
        System.out.println("Sender object: " + user.toString());
        rabbitTemplate.convertAndSend("object", user);
    }
}
