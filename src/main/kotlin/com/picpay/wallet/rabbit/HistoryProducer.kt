package com.picpay.wallet.rabbit

import com.picpay.wallet.entity.History
import com.picpay.wallet.util.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Slf4j
@Component
class HistoryProducer(
    private val objectMapper: ObjectMapper,
    private val rabbitTemplate: RabbitTemplate,
    private val historyQueue: Queue
) {
    private val log = LoggerFactory.getLogger(HistoryProducer::class.java)
    fun sender(history: History) {
        log.info("M=sender, message=Init send historic to Rabbit, wallet={}", history.wallet.account)
        rabbitTemplate.convertAndSend(historyQueue.name,objectMapper.serialize(history))
    }
}