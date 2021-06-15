package com.example.kotlincompiler.rabbit

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan
open class RabbitConfiguration {
    companion object {
        final val topicExchangeName = "spring-boot-exchange"

        val queueName = "coco"
    }


    @Bean
    open fun queue(): Queue? {
        return Queue(queueName, true)
    }

    @Bean
    open fun exchange(): TopicExchange? {
        return TopicExchange(topicExchangeName)
    }

    @Bean
    open fun binding(queue: Queue?, exchange: TopicExchange?): Binding? {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#")
    }

    @Bean
    open fun container(
        connectionFactory: ConnectionFactory,
        listenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer? {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(queueName)
        container.setMessageListener(listenerAdapter)
        return container
    }

    @Bean
    open fun listenerAdapter(receiver: CodeReceiver?): MessageListenerAdapter? {
        return MessageListenerAdapter(receiver, "consume")
    }
}

