package com.picpay.wallet.dto

import com.picpay.wallet.entity.DocumentType
import java.time.LocalDate
import java.time.LocalDateTime

data class ClienteDTO(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val birthday: LocalDate,
    val document: String,
    val documentType: DocumentType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val wallet: WalletDTO
)