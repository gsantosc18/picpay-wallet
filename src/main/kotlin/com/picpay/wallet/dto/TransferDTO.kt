package com.picpay.wallet.dto

data class TransferDTO(
    val destination: Int,
    val sender: Int,
    val value: Double
)