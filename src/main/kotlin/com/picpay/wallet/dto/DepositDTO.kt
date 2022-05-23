package com.picpay.wallet.dto

import javax.validation.constraints.Min

data class DepositDTO (
    val account: Int,
    @Min(0)
    val value: Double
)