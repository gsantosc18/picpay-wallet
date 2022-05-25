package com.picpay.wallet.service

import com.picpay.wallet.dto.*

interface WalletService {
    fun withdrawal(withdrawDTO: WithdrawDTO): WalletDTO
    fun transfer(transferDTO: TransferDTO): WalletDTO
    fun deposit(depositDTO: DepositDTO): WalletDTO
    fun payDebit(payDebitDTO: PayDebitDTO): WalletDTO
    fun find(id: Int): WalletDTO
}