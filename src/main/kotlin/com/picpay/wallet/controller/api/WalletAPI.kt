package com.picpay.wallet.controller.api

import com.picpay.wallet.dto.WalletDTO
import com.picpay.wallet.dto.WithdrawDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Api(value = "/wallet", description = "Endpoint para movimentação da carteira do cliente")
@RequestMapping("/wallet")
interface WalletAPI {
    @ApiOperation(value = "Sacar valor de uma conta existente")
    @PostMapping
    fun withdrawal(withdrawDTO: WithdrawDTO): ResponseEntity<WalletDTO>
}