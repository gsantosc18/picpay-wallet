package com.picpay.wallet.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableRabbit
@Configuration
class RabbitMQConfig{
    @Value("\${queue.history}")
    private var history: String? = null

    @Bean
    fun historyQueue() = Queue(history, true)
}