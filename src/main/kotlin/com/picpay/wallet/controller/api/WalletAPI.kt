package com.picpay.wallet.controller.api

import com.picpay.wallet.dto.*
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Api(value = "/wallet", description = "Endpoint para movimentação da carteira do cliente")
@RequestMapping("/wallet")
interface WalletAPI {
    @ApiOperation(value = "Sacar valor de uma carteira existente")
    @PostMapping("/withdrawal")
    fun withdrawal(withdrawDTO: WithdrawDTO): ResponseEntity<WalletDTO>

    @ApiOperation(value = "Transferir valor entre carteiras")
    @PostMapping("/transfer")
    fun transfer(transferDTO: TransferDTO): ResponseEntity<WalletDTO>

    @ApiOperation(value = "Depositar valor em uma carteira")
    @PostMapping("/deposit")
    fun deposit(depositDTO: DepositDTO): ResponseEntity<WalletDTO>

    @ApiOperation(value = "Pagar conta com saldo de carteira")
    @PostMapping("/paydebit")
    fun payDebit(payDebitDTO: PayDebitDTO): ResponseEntity<WalletDTO>

    @ApiOperation(value = "Busca carteira pelo id")
    @PostMapping("/{id}")
    fun findWallet(id: Int): ResponseEntity<WalletDTO>
}