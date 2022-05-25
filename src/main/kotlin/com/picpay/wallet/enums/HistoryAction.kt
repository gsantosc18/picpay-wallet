package com.picpay.wallet.enums

enum class HistoryAction(
    val value: String
) {
    WITHDRAWAL("withdrawal"),
    PAY_DEBIT("pay_debit"),
    DEPOSIT("deposit"),
    TRANSFER_SENT("transfer_sent"),
    TRANSFER_RECEIVED("transfer_received")
}