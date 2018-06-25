package com.cfc.service.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Component;

/**
 * @auther fangchen.chai ON 2018/1/24
 */
@Component
public class FanoutRabbitReceiver {

    @RabbitHandler
    @RabbitListener(queues = "fanout.C")
    public void processC(String msg) {
        System.out.println(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "fanout.B")
    public void processB(String msg) {
        System.out.println(msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "fanout.A")
    public void processA(String msg) {
        System.out.println(msg);
    }
}
