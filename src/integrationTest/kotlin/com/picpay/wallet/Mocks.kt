package com.picpay.wallet

import com.picpay.wallet.dto.CreateClienteDTO
import com.picpay.wallet.dto.UpdateClienteDTO
import com.picpay.wallet.entity.Cliente
import com.picpay.wallet.entity.DocumentType
import com.picpay.wallet.entity.Wallet
import java.time.LocalDate

const val CLIENTE_NAME = "Fulano"
const val CLIENTE_LASTNAME = "Tal"
const val CLIENTE_EMAIL = "fulanodetal@email.com"
const val DOCUMENT_NUMBER = "123456789"

fun createClienteDtoMock() = CreateClienteDTO(
    name = CLIENTE_NAME,
    lastName = CLIENTE_LASTNAME,
    email = CLIENTE_EMAIL,
    birthday = LocalDate.now(),
    document = DOCUMENT_NUMBER,
    documentType = DocumentType.CPF
)

fun updateClientDTOMock() = UpdateClienteDTO(
    name = "Siclano",
    lastName = "Silva",
    email = CLIENTE_EMAIL,
    birthday = LocalDate.now(),
    document = DOCUMENT_NUMBER,
    documentType = DocumentType.CPF
)

fun clienteMock() =
    Cliente(
        name = CLIENTE_NAME,
        lastName = CLIENTE_LASTNAME,
        email = CLIENTE_EMAIL,
        birthday = LocalDate.now(),
        document = DOCUMENT_NUMBER,
        documentType = DocumentType.CPF
    )

fun walletMock() = Wallet(
    account = 1,
    balance = 10.0,
    cliente = clienteMock()
)

fun walletMock(cliente: Cliente) = Wallet(
    account = 2,
    balance = 10.0,
    cliente = cliente
)