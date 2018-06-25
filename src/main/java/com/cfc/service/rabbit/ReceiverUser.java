package com.cfc.service.rabbit;

import com.cfc.dao.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @auther fangchen.chai ON 2018/1/24
 */
@Component

public class ReceiverUser {

    @RabbitHandler
    @RabbitListener(queues = "object")
    public void process(User user) {
        System.out.println("Receiver object  : " + user);
    }

}
