package com.picpay.wallet.dto

import java.time.LocalDateTime

class WalletDTO(
    val account: Int,
    val balance: Double,
    val historys: List<HistoryDTO>?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
)