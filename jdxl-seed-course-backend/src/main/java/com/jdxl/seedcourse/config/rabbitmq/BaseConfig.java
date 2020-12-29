package com.jdxl.seedcourse.config.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * <p> Queue配置基类
 *
 * @author Moccanism
 * @version 1.0.0
 * @since 2020/1/16
 */
public class BaseConfig {

    /**
     * 配置 SimpleMessageListenerContainer
     *
     * @param connectionFactory connectionFactory
     * @param listenerChangeMobileAdapter listenerChangeMobileAdapter
     * @param queueName queueName
     * @param maxConcurrentConsumerCount maxConcurrentConsumerCount
     * @param concurrentConsumerCount  concurrentConsumerCount
     * @param prefetchCount maxConcurrentConsumerCount
     * @param acknowledgeMode acknowledgeMode
     *
     * @return SimpleMessageListenerContainer
     */
    SimpleMessageListenerContainer messageListenerContainer (
            ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerChangeMobileAdapter,
            String queueName,
            int maxConcurrentConsumerCount,
            int concurrentConsumerCount,
            int prefetchCount,
            AcknowledgeMode acknowledgeMode) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(maxConcurrentConsumerCount);
        container.setConcurrentConsumers(concurrentConsumerCount);
        container.setPrefetchCount(prefetchCount);
        container.setAcknowledgeMode(acknowledgeMode);
        container.setMessageListener(listenerChangeMobileAdapter);
        return container;
    }

    /**
     * 配置SimpleMessageListenerContainer, prefetchCount默认250
     *
     * @param connectionFactory connectionFactory
     * @param listenerAdapter listenerAdapter
     * @param queueName queueName
     * @param maxConcurrentConsumerCount maxConcurrentConsumerCount
     * @param concurrentConsumerCount  concurrentConsumerCount
     * @param acknowledgeMode acknowledgeMode
     *
     * @return SimpleMessageListenerContainer
     */
    SimpleMessageListenerContainer messageListenerContainer (
            ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter,
            String queueName,
            int maxConcurrentConsumerCount,
            int concurrentConsumerCount,
            AcknowledgeMode acknowledgeMode) {


        return messageListenerContainer(connectionFactory,
                listenerAdapter,
                queueName,
                maxConcurrentConsumerCount,
                concurrentConsumerCount,
                250,
                acknowledgeMode);

    }
}
