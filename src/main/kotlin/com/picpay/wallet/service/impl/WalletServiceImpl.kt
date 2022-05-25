package com.picpay.wallet.service.impl

import com.picpay.wallet.dto.*
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.enums.HistoryAction
import com.picpay.wallet.enums.HistoryAction.*
import com.picpay.wallet.exception.DestinationNotFoundException
import com.picpay.wallet.exception.InsuficienteBalanceException
import com.picpay.wallet.exception.InvalidValueException
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.repository.WalletRepository
import com.picpay.wallet.service.HistoryService
import com.picpay.wallet.service.WalletService
import com.picpay.wallet.util.walletToDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WalletServiceImpl(
    private val walletRepository: WalletRepository,
    private val historyService: HistoryService
): WalletService {
    private val log = LoggerFactory.getLogger(WalletServiceImpl::class.java)
    override fun withdrawal(withdrawDTO: WithdrawDTO): WalletDTO {
        log.info("M=withdrawal, message=Init withdrawal, wallet={}", withdrawDTO.account)
        validateValue(withdrawDTO.value)
        var wallet = walletRepository.findById(withdrawDTO.account).orElseThrow{NotFoundClientException()}
        val value = withdrawDTO.value

        validateBalance(wallet, value)

        wallet.balance -= value
        saveWalletAndHistory(wallet, WITHDRAWAL)
        log.info("M=withdrawal, message=Success withdrawal from wallet, wallet={}", withdrawDTO.account)
        return walletToDTO(wallet)
    }

    override fun transfer(transferDTO: TransferDTO): WalletDTO {
        log.info("M=transfer, message=Init transfer between wallets, sender={}, destination={}, value={}",
            transferDTO.sender, transferDTO.destination, transferDTO.value)
        validateValue(transferDTO.value)
        val sender = walletRepository.findById(transferDTO.sender).orElseThrow{NotFoundClientException()}
        val destination = walletRepository.findById(transferDTO.destination).orElseThrow{DestinationNotFoundException()}
        val value = transferDTO.value
        validateBalance(sender, value)

        sender.balance -= value
        destination.balance += value

        saveWalletAndHistory(sender, TRANSFER_SENT)
        saveWalletAndHistory(destination, TRANSFER_RECEIVED)
        log.info("M=transfer, message=Success transfer between wallets, sender={}, destination={}",
            transferDTO.sender, transferDTO.destination)

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
        historyService.save(wallet, action)
        walletRepository.save(wallet)
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