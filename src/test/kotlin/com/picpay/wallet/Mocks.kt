package com.picpay.wallet

import com.picpay.wallet.dto.CreateClienteDTO
import com.picpay.wallet.dto.UpdateClienteDTO
import com.picpay.wallet.dto.WithdrawDTO
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

fun clienteMock() = Cliente(
    id = 1,
    name = CLIENTE_NAME,
    lastName = CLIENTE_LASTNAME,
    email = CLIENTE_EMAIL,
    birthday = LocalDate.now(),
    document = DOCUMENT_NUMBER,
    documentType = DocumentType.CPF,
    wallet = Wallet(
        account = 1,
        balance = 10.0,
        cliente = Cliente(
            id = 1,
            name = CLIENTE_NAME,
            lastName = CLIENTE_LASTNAME,
            email = CLIENTE_EMAIL,
            birthday = LocalDate.now(),
            document = DOCUMENT_NUMBER,
            documentType = DocumentType.CPF,
        )
    )
)

fun updateClientDTOMock() = UpdateClienteDTO(
    name = "Siclano",
    lastName = "Silva",
    email = CLIENTE_EMAIL,
    birthday = LocalDate.now(),
    document = DOCUMENT_NUMBER,
    documentType = DocumentType.CPF
)

fun walletMock() =
    Wallet(
        account = 1,
        balance = 10.0,
        cliente = clienteMock()
    )

fun walletDTOMock() = WithdrawDTO(
        account = 1,
        value = 5.0
    )
