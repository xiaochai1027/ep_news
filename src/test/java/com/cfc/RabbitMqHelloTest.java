package com.cfc;

import com.cfc.dao.model.User;
import com.cfc.service.rabbit.FanoutRabbitSender;
import com.cfc.service.rabbit.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @auther fangchen.chai ON 2018/1/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqHelloTest {

    @Autowired
    private HelloSender helloSender;
    @Autowired
    private FanoutRabbitSender fanoutRabbitSender;


    @Test
    public void hello() throws Exception {
        helloSender.send();
    }

    /**
     * 一个发送者，N个接受者,经过测试会均匀的将消息发送到N个接收者中
     * @throws Exception
     */
    @Test
    public void oneToMany() throws Exception {
        for (int i=0;i<100;i++){
            helloSender.neoSender(i);
        }
    }

    @Test
    public void sendObject() throws Exception {
        User user = new User();
        user.setAge(18);
        user.setBirthday(new Date());
        user.setName("fangchen");
        helloSender.send(user);

    }

    /**
     * 发布订阅模式，把队列绑定到交换机上，向交换机发送消息，交换机把消息发送到所有绑定到的队列上
     * @throws Exception
     */
    @Test
    public void Topic(){
        fanoutRabbitSender.sendTopic();
    }

    /**
     * 只想绑定到交换机上的A发送数据
     */
    @Test
    public void fanoutA(){
        fanoutRabbitSender.sendA();
    }


}
