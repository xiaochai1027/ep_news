package com.cfc.service.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther fangchen.chai ON 2018/1/24
 */
@Component
public class FanoutRabbitSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendTopic() {
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
    }

    public void sendA() {
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanout.A", context);
    }
}
