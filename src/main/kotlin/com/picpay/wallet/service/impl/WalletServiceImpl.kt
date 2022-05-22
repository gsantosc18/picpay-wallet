package com.picpay.wallet.service.impl

import com.picpay.wallet.dto.TransferDTO
import com.picpay.wallet.dto.WalletDTO
import com.picpay.wallet.dto.WithdrawDTO
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.exception.DestinationNotFoundException
import com.picpay.wallet.exception.InsuficienteBalanceException
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.repository.WalletRepository
import com.picpay.wallet.service.WalletService
import com.picpay.wallet.util.walletToDTO
import org.springframework.stereotype.Service

@Service
class WalletServiceImpl(
    private val walletRepository: WalletRepository
): WalletService {
    override fun withdrawal(withdrawDTO: WithdrawDTO): WalletDTO {
        var wallet = walletRepository.findById(withdrawDTO.account).orElseThrow{NotFoundClientException()}
        val value = withdrawDTO.value

        validateBalance(wallet, value)

        wallet.balance -= value
        walletRepository.save(wallet)
        return WalletDTO(account = wallet.account!!, balance = wallet.balance)
    }

    override fun transfer(transferDTO: TransferDTO): WalletDTO {
        val sender = walletRepository.findById(transferDTO.sender).orElseThrow{NotFoundClientException()}
        val destination = walletRepository.findById(transferDTO.destination).orElseThrow{DestinationNotFoundException()}
        val value = transferDTO.value
        validateBalance(sender, value)

        sender.balance -= value
        destination.balance += value

        walletRepository.save(sender)
        walletRepository.save(destination)

        return walletToDTO(sender)
    }

    fun validateBalance(wallet: Wallet, value: Double) {
        if(wallet.balance.minus(value) < 0) {
            throw InsuficienteBalanceException()
        }
    }
}