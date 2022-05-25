package com.picpay.wallet.dto

import com.picpay.wallet.enums.DocumentType
import java.time.LocalDate

data class CreateClientDTO(
    val name: String,
    val lastName: String,
    val email: String,
    val birthday: LocalDate,
    val document: String,
    val documentType: DocumentType
)