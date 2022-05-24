package com.picpay.wallet.service.impl

import com.picpay.wallet.dto.*
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.enums.HistoryAction
import com.picpay.wallet.enums.HistoryAction.*
import com.picpay.wallet.exception.DestinationNotFoundException
import com.picpay.wallet.exception.InsuficienteBalanceException
import com.picpay.wallet.exception.InvalidValueException
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.rabbit.HistoryProducer
import com.picpay.wallet.repository.WalletRepository
import com.picpay.wallet.service.HistoryService
import com.picpay.wallet.service.WalletService
import com.picpay.wallet.util.walletToDTO
import org.springframework.stereotype.Service

@Service
class WalletServiceImpl(
    private val walletRepository: WalletRepository,
    private val historyService: HistoryService,
    private val historyProducer: HistoryProducer
): WalletService {
    override fun withdrawal(withdrawDTO: WithdrawDTO): WalletDTO {
        validateValue(withdrawDTO.value)
        var wallet = walletRepository.findById(withdrawDTO.account).orElseThrow{NotFoundClientException()}
        val value = withdrawDTO.value

        validateBalance(wallet, value)

        wallet.balance -= value
        saveWalletAndHistory(wallet, WITHDRAWAL)
        return walletToDTO(wallet)
    }

    override fun transfer(transferDTO: TransferDTO): WalletDTO {
        validateValue(transferDTO.value)
        val sender = walletRepository.findById(transferDTO.sender).orElseThrow{NotFoundClientException()}
        val destination = walletRepository.findById(transferDTO.destination).orElseThrow{DestinationNotFoundException()}
        val value = transferDTO.value
        validateBalance(sender, value)

        sender.balance -= value
        destination.balance += value

        saveWalletAndHistory(sender, TRANSFER)
        saveWalletAndHistory(destination, TRANSFER)

        return walletToDTO(sender)
    }

    override fun deposit(depositDTO: DepositDTO): WalletDTO {
        validateValue(depositDTO.value)
        val savedWallet = walletRepository.findById(depositDTO.account).orElseThrow { NotFoundClientException() }

        savedWallet.balance += depositDTO.value

        saveWalletAndHistory(savedWallet, DEPOSIT)

        return walletToDTO(savedWallet)
    }

    override fun payDebit(payDebitDTO: PayDebitDTO): WalletDTO {
        validateValue(payDebitDTO.value)
        val savedWallet = walletRepository.findById(payDebitDTO.account).orElseThrow { NotFoundClientException() }

        savedWallet.balance -= payDebitDTO.value

        saveWalletAndHistory(savedWallet, PAY_DEBIT)

        return walletToDTO(savedWallet)
    }

    private fun saveWalletAndHistory(wallet: Wallet, action: HistoryAction) {
        val history = historyService.save(wallet, action)
        wallet.addHistory(history)
        walletRepository.save(wallet)
        historyProducer.sender(history)
    }

    private fun validateBalance(wallet: Wallet, value: Double) {
        if(wallet.balance.minus(value) < 0) {
            throw InsuficienteBalanceException()
        }
    }

    private fun validateValue(value: Double) {
        if (value < 0) throw InvalidValueException()
    }
}