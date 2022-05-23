package com.picpay.wallet.controller

import com.picpay.wallet.controller.api.WalletAPI
import com.picpay.wallet.dto.*
import com.picpay.wallet.exception.InsuficienteBalanceException
import com.picpay.wallet.exception.InvalidValueException
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.service.WalletService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class WalletController(
    private val walletService: WalletService
): WalletAPI {
    override fun withdrawal(@RequestBody withdrawDTO: WithdrawDTO): ResponseEntity<WalletDTO> =
        status(HttpStatus.OK).body(walletService.withdrawal(withdrawDTO))

    override fun transfer(@RequestBody transferDTO: TransferDTO): ResponseEntity<WalletDTO> =
        status(OK).body(walletService.transfer(transferDTO))

    override fun deposit(@RequestBody depositDTO: DepositDTO): ResponseEntity<WalletDTO> =
        status(OK).body(walletService.deposit(depositDTO))

    override fun payDebit(@RequestBody payDebitDTO: PayDebitDTO): ResponseEntity<WalletDTO> =
        status(OK).body(walletService.payDebit(payDebitDTO))

    @ExceptionHandler(value = [InsuficienteBalanceException::class, NotFoundClientException::class, InvalidValueException::class])
    fun exceptionHandler(runtimeException: RuntimeException) =
        status(BAD_REQUEST).body(mapOf("message" to runtimeException.message))
}