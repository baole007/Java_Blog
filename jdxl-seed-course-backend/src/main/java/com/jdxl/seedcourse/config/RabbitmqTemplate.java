package com.jdxl.seedcourse.config;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 这是 RabbitTemplate 的封装类
 *
 * 不建议去复写 RabbitTemplate 的方法 除非你非常确定这样做是正确的
 * 使用时均使用 RabbitmqTemplate 中的方法
 *
 */
@Slf4j
public class RabbitmqTemplate extends RabbitTemplate
{
    public RabbitmqTemplate(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    public void setMq(String msg, String exchangeName, String routingKey){
        try {
            convertAndSend(exchangeName, routingKey, msg);
        } catch (AmqpException e) {
            log.error("payment rabbitmq error : {}" + e, e);
        }
    }

    public void setMq(String exchange, String key, String content, long millisecond) {
        try {
            // 添加延时队列
            convertAndSend(exchange, key, content, message -> {
                // 第一句是可要可不要,根据自己需要自行处理
                //message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Event.class.getName());
                // 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
                message.getMessageProperties().setExpiration(millisecond + "");
                return message;
            });
            log.info("setMqerr 发送时间 = {}, content = {}", LocalDateTime.now(), content);
        } catch (Exception e) {
            log.error("setMqerr 发送时间 = {}, content = {}", LocalDateTime.now(), content);
        }
    }

    /**
     * 应答rabbitmq 数据处理情况
     */
    public void ack(Message message, Channel channel, boolean dealSuccess) {
        try {
            if (dealSuccess) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), Boolean.FALSE);
            } else {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), Boolean.FALSE, Boolean.TRUE);
            }
        } catch (IOException e) {
            log.error("ACKRabbitMq ： rabbitmq连接异常.IOException: {} " + e, e);
            // TODO: 异常报警，rabbitmq连接错误。
        }

    }
}
