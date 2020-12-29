package com.jdxl.seedcourse.consumer;

import com.jdxl.seedcourse.config.RabbitmqTemplate;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConsumer implements ChannelAwareMessageListener {

    @Autowired
    private RabbitmqTemplate rabbitmqTemplate;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        try {
            // TODO 业务逻辑
            log.info(new String(message.getBody()));
        } catch (Exception e) {
            log.error("OrderReceiver Error", e);
        } finally {
            rabbitmqTemplate.ack(message, channel, Boolean.TRUE);
        }
    }
}
