package com.jdxl.seedcourse.config.rabbitmq;

import com.jdxl.seedcourse.constant.RabbitmqConstantInterface;
import com.jdxl.seedcourse.consumer.OrderConsumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMqConfig extends BaseConfig {

    private static String getQueueName() { return RabbitmqConstantInterface.ORDER_QUEUE; }
    private static String getExchangeName() { return RabbitmqConstantInterface.ORDER_EXCHANGE; }

    private ConnectionFactory connectionFactory;

    @Autowired
    public void setConnectionFactory(ConnectionFactory amqpConnectionFactory){
        this.connectionFactory = amqpConnectionFactory;
    }

    /**
     * 配置queue
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(getQueueName(), true);
    }

    /**
     *  配置exchange
     */
    @Bean
    public FanoutExchange orderExchange() {
        return new FanoutExchange(getExchangeName());
    }

    /**
     * 绑定队列与交换机
     *
     * @return Binding
     */
    @Bean
    Binding bindingCommonMissionExchangeNotify(Queue orderQueue, FanoutExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange);
    }

    /**
     * 配置消费者
     */
    @Bean
    @ConditionalOnProperty(name = "jdxl.rabbitmq.consumer.enable", havingValue = "true", matchIfMissing = false)
    public OrderConsumer CommonMissionReceiver() {
        return new OrderConsumer();
    }

    /**
     * 配置 ListenerAdapter
     */
    @Bean
    @ConditionalOnProperty(name = "jdxl.rabbitmq.consumer.enable", havingValue = "true", matchIfMissing = false)
    MessageListenerAdapter orderListenerAdapter(OrderConsumer orderConsumer) {
        return new MessageListenerAdapter(orderConsumer, "onMessage");
    }

    /**
     * 配置 ListenerContainer
     */
    @Bean
    @ConditionalOnProperty(name = "jdxl.rabbitmq.consumer.enable", havingValue = "true", matchIfMissing = false)
    public SimpleMessageListenerContainer orderListenerContainer(MessageListenerAdapter orderListenerAdapter) {

        return super.messageListenerContainer(connectionFactory,
                orderListenerAdapter,
                getQueueName(),
                3,
                1,
                3,
                AcknowledgeMode.MANUAL);
    }

}
