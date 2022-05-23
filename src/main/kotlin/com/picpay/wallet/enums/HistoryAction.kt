package com.picpay.wallet.enums

enum class HistoryAction(
    val value: String
) {
    WITHDRAWAL("withdrawal"),
    PAY_DEBIT("pay_debit"),
    DEPOSIT("deposit"),
    TRANSFER("transfer")
}