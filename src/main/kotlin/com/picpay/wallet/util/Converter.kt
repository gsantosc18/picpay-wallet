package com.picpay.wallet.util

import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClientDTO
import com.picpay.wallet.dto.WalletDTO
import com.picpay.wallet.entity.Client
import com.picpay.wallet.entity.Wallet

fun createDTOToClient(createClientDTO: CreateClientDTO) =
    Client(
        name = createClientDTO.name,
        lastName = createClientDTO.lastName,
        email = createClientDTO.email,
        birthday = createClientDTO.birthday,
        document = createClientDTO.document,
        documentType = createClientDTO.documentType
    )

fun clientToDTO(client: Client) =
    ClienteDTO(
        id = client.id!!,
        name = client.name,
        lastName = client.lastName,
        email = client.email,
        birthday = client.birthday,
        document = client.document,
        documentType = client.documentType,
        wallet = WalletDTO(
            account = client.wallet?.account!!,
            balance = client.wallet?.balance!!
        )
    )

fun walletToDTO(wallet: Wallet) =
    WalletDTO(
        account = wallet.account!!,
        balance = wallet.balance
    )