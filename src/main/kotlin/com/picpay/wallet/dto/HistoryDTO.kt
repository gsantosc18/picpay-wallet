package com.picpay.wallet.dto

import com.picpay.wallet.enums.HistoryAction
import java.time.LocalDateTime

class HistoryDTO(
    val action: HistoryAction,
    val createdAt: LocalDateTime
)