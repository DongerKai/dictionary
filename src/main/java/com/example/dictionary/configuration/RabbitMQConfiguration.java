package com.example.dictionary.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

import static com.example.dictionary.common.constant.StringConstant.DEFAULT_BOOK_QUEUE;
import static com.example.dictionary.common.constant.StringConstant.MANUAL_BOOK_QUEUE;

/**
 * RabbitMQ配置
 *
 * @author DongerKai
 * @since 2019/6/19 13:35 ，1.0
 **/
@Configuration
@Slf4j
public class RabbitMQConfiguration {

    @Bean
    public Queue defaultBookQueue(){
        // 第一个是 QUEUE 的名字,第二个是消息是否需要持久化处理
        return new Queue(DEFAULT_BOOK_QUEUE, true);
    }

    @Bean
    public Queue manualBookQueue(){
        return new Queue(MANUAL_BOOK_QUEUE, true);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)-> log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message));
        return rabbitTemplate;
    }

    /**
     * 延迟队列 TTL 名称
     */
    private static final String REGISTER_DELAY_QUEUE = "dev.book.register.delay.queue";
    /**
     * DLX，dead letter发送到的 exchange
     * TODO 此处的 exchange 很重要,具体消息就是发送到该交换机的
     */
    public static final String REGISTER_DELAY_EXCHANGE = "dev.book.register.delay.exchange";
    /**
     * routing key 名称
     * TODO 此处的 routingKey 很重要,具体消息发送在该 routingKey 的
     */
    public static final String DELAY_ROUTING_KEY = "";

    public static final String REGISTER_QUEUE_NAME = "dev.book.register.queue";
    public static final String REGISTER_EXCHANGE_NAME = "dev.book.register.exchange";
    public static final String ROUTING_KEY = "all";

    @Bean
    public Queue delayProcessQueue(){
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", REGISTER_EXCHANGE_NAME);
        params.put("x-dead-letter-routing-key", ROUTING_KEY);
        return new Queue(REGISTER_DELAY_QUEUE, true, false, false, params);
    }

    @Bean
    public DirectExchange delayExchange(){
        return new DirectExchange(REGISTER_DELAY_EXCHANGE);
    }

    @Bean
    public Binding dlxBinding(){
        return BindingBuilder.bind(delayProcessQueue()).to(delayExchange()).with(DELAY_ROUTING_KEY);
    }

    @Bean
    public Queue registerBookQueue(){
        return new Queue(REGISTER_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange registerBookTopicExchange(){
        return new TopicExchange(REGISTER_EXCHANGE_NAME);
    }

    @Bean
    public Binding registerBookBinding(){
        return BindingBuilder.bind(registerBookQueue()).to(registerBookTopicExchange()).with(ROUTING_KEY);
    }

}
