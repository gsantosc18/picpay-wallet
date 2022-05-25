package com.picpay.wallet

import com.picpay.wallet.dto.*
import com.picpay.wallet.entity.Client
import com.picpay.wallet.entity.DocumentType
import com.picpay.wallet.entity.History
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.enums.HistoryAction
import com.picpay.wallet.enums.HistoryAction.WITHDRAWAL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now

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

fun clienteMock() = Client(
    id = 1,
    name = CLIENTE_NAME,
    lastName = CLIENTE_LASTNAME,
    email = CLIENTE_EMAIL,
    birthday = LocalDate.now(),
    document = DOCUMENT_NUMBER,
    documentType = DocumentType.CPF,
    createdAt = now(),
    wallet = Wallet(
        account = 1,
        balance = 10.0,
        client = Client(
            id = 1,
            name = CLIENTE_NAME,
            lastName = CLIENTE_LASTNAME,
            email = CLIENTE_EMAIL,
            birthday = LocalDate.now(),
            document = DOCUMENT_NUMBER,
            documentType = DocumentType.CPF,
        ),
        createdAt = now()
    )
)

fun updateClientDTOMock() = UpdateClientDTO(
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
        client = clienteMock(),
        createdAt = now(),
    )

fun secondWalletMock() =
    Wallet(
        account = 2,
        balance = 5.0,
        client = clienteMock(),
        createdAt = now()
    )

fun walletDTOMock() = WithdrawDTO(
        account = 1,
        value = 5.0
    )

fun transferDTOMock() = TransferDTO(
    sender = 1,
    destination = 2,
    value = 10.0
)

fun depositDTOMock() = DepositDTO(
    account = 1,
    value = 5.0
)

fun walletDepositMock() =
    Wallet(
        account = 1,
        balance = 15.0,
        client = clienteMock(),
        createdAt = now()
    )

fun payDebitDTOMock() = PayDebitDTO(
    account = 1,
    value = 5.0
)

fun payDebitDTONegativeMock() = PayDebitDTO(
    account = 1,
    value = -5.0
)

fun historyMock() = History(
    wallet = walletMock(),
    historyAction = WITHDRAWAL
)
