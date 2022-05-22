package com.picpay.wallet.dto

import com.picpay.wallet.entity.DocumentType
import java.time.LocalDate

data class ClienteDTO(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val birthday: LocalDate,
    val document: String,
    val documentType: DocumentType,
    val wallet: WalletDTO
)