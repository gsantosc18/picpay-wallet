package com.picpay.wallet.dto

import com.picpay.wallet.entity.DocumentType
import java.time.LocalDate

class ClienteDTO(
    var id: Int,
    var name: String,
    var lastName: String,
    var email: String,
    var birthday: LocalDate,
    var document: String,
    var documentType: DocumentType,
    var wallet: WalletDTO
)