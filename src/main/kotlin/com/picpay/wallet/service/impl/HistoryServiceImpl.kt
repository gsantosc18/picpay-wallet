package com.picpay.wallet.service.impl

import com.picpay.wallet.entity.History
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.enums.HistoryAction
import com.picpay.wallet.repository.HistoryRepository
import com.picpay.wallet.service.HistoryService
import org.springframework.stereotype.Service

@Service
class HistoryServiceImpl(
    val historyRepository: HistoryRepository
): HistoryService {
    override fun save(wallet: Wallet, action: HistoryAction): History {
        val history = History(
            wallet = wallet,
            historyAction = action
        )
        return historyRepository.save(history)
    }
}