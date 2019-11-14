package com.example.dictionary.base.handler;

import com.example.dictionary.model.dataobject.BookDo;
import com.example.dictionary.model.dataobject.UserDo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.example.dictionary.common.constant.StringConstant.DEFAULT_BOOK_QUEUE;
import static com.example.dictionary.common.constant.StringConstant.MANUAL_BOOK_QUEUE;
import static com.example.dictionary.configuration.RabbitMQConfiguration.REGISTER_QUEUE_NAME;

/**
 * USER_QUEUE 消费者
 *
 * @author DongerKai
 * @since 2019/6/20 9:14 ，1.0
 **/
@Component
@Slf4j
public class UserHandler {
    @RabbitListener(queues = {DEFAULT_BOOK_QUEUE})
    public void listenAutoAck(UserDo user, Message message, Channel channel){
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[ListenAutoAck 监听的消息] -[{}]", user.toString());
            channel.basicAck(deliveryTag, false);
        }catch (IOException e){
            try {
                channel.basicRecover();
            }catch (IOException el){
                el.printStackTrace();
            }
        }
    }

    @RabbitListener(queues = {MANUAL_BOOK_QUEUE})
    public void listenManualAck(UserDo user, Message message, Channel channel){
        log.info("[listenManualAck 监听的消息] - [{}]", user.toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (IOException e){
            log.info("消息失败");
        }
    }

    @RabbitListener(queues = {REGISTER_QUEUE_NAME})
    public void listenDelayAck(BookDo book, Message message, Channel channel){
        log.info("监听消息 消费的时间：{}， 结果:{}", LocalDateTime.now(), book.toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //do something
        }
    }
}