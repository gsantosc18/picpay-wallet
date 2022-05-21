package com.picpay.wallet.service

import com.picpay.wallet.dto.WalletDTO
import com.picpay.wallet.dto.WithdrawDTO

interface WalletService {
    fun withdrawal(withdrawDTO: WithdrawDTO): WalletDTO
}