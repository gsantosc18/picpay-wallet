package com.picpay.wallet.service.impl

import com.picpay.wallet.entity.History
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.enums.HistoryAction
import com.picpay.wallet.rabbit.HistoryProducer
import com.picpay.wallet.repository.HistoryRepository
import com.picpay.wallet.service.HistoryService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class HistoryServiceImpl(
    private val historyRepository: HistoryRepository,
    private val historyProducer: HistoryProducer
): HistoryService {
    private val log = LoggerFactory.getLogger(HistoryServiceImpl::class.java)
    override fun save(wallet: Wallet, action: HistoryAction) {
        log.info("M=save, message=Init save history, wallet={}, action={}", wallet.account, action)
        val history = History(
            wallet = wallet,
            historyAction = action
        )
        val savedHistory = historyRepository.save(history)
        historyProducer.sender(savedHistory)
        wallet.addHistory(savedHistory)
        log.info("M=save, message=Success on create historic, wallet={}, action={}", wallet.account, action)
    }
}