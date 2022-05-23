package com.picpay.wallet.service

import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.enums.HistoryAction

interface HistoryService {
    fun save(wallet: Wallet, action: HistoryAction)
}