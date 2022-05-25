package com.picpay.wallet

import com.picpay.wallet.dto.*
import com.picpay.wallet.entity.Client
import com.picpay.wallet.enums.DocumentType
import com.picpay.wallet.entity.Wallet
import java.time.LocalDate

const val CLIENTE_NAME = "Fulano"
const val CLIENTE_LASTNAME = "Tal"
const val CLIENTE_EMAIL = "fulanodetal@email.com"
const val DOCUMENT_NUMBER = "123456789"

fun createClienteDtoMock() = CreateClientDTO(
    name = CLIENTE_NAME,
    lastName = CLIENTE_LASTNAME,
    email = CLIENTE_EMAIL,
    birthday = LocalDate.now(),
    document = DOCUMENT_NUMBER,
    documentType = DocumentType.CPF
)

fun updateClientDTOMock() = UpdateClientDTO(
    name = "Siclano",
    lastName = "Silva",
    email = CLIENTE_EMAIL,
    birthday = LocalDate.now(),
    document = DOCUMENT_NUMBER,
    documentType = DocumentType.CPF
)

fun clienteMock() =
    Client(
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
    client = clienteMock()
)

fun walletMock(client: Client) = Wallet(
    account = 2,
    balance = 10.0,
    client = client
)

fun transferDTOMock() = TransferDTO(
    sender = 1,
    destination = 2,
    value = 5.0
)

fun invalidTransferDTOMock() = TransferDTO(
    sender = 1,
    destination = 2,
    value = 15.0
)

fun senderNotExistTransferDTOMock() = TransferDTO(
    sender = 100,
    destination = 2,
    value = 15.0
)

fun depositDTOMock() = DepositDTO(
    account = 1,
    value = 15.0
)

fun depositDTOValueNegativeMock() = DepositDTO(
    account = 1,
    value = -10.0
)

fun payDebitDTOMock() = PayDebitDTO(
    account = 1,
    value = 5.0
)

fun payDebitDTONonexistentWalletMock() = PayDebitDTO(
    account = 100,
    value = 5.0
)

fun payDebitDTOInvalidValueMock() = PayDebitDTO(
    account = 1,
    value = -5.0
)
