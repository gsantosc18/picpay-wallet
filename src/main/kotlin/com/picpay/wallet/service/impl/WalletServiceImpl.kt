package com.picpay.wallet.service.impl

import com.picpay.wallet.dto.WithdrawDTO
import com.picpay.wallet.exception.InsuficienteBalanceException
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.repository.WalletRepository
import com.picpay.wallet.service.WalletService
import org.springframework.stereotype.Service

@Service
class WalletServiceImpl(
    private val walletRepository: WalletRepository
): WalletService {
    override fun withdrawal(withdrawDTO: WithdrawDTO): WithdrawDTO {
        var wallet = walletRepository.findById(withdrawDTO.account).orElseThrow{NotFoundClientException()}
        val balance = wallet.balance
        val value = balance.minus(withdrawDTO.value)

        if (value < 0) {
            throw InsuficienteBalanceException()
        }

        wallet.balance = value
        walletRepository.save(wallet)
        return WithdrawDTO(account = wallet.account!!, value = value)
    }
}