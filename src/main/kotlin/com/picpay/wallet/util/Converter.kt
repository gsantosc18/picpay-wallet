package com.picpay.wallet.util

import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClienteDTO
import com.picpay.wallet.dto.WalletDTO
import com.picpay.wallet.entity.Cliente

fun createDTOToCliente(createClienteDTO: CreateClienteDTO) =
    Cliente(
        name = createClienteDTO.name,
        lastName = createClienteDTO.lastName,
        email = createClienteDTO.email,
        birthday = createClienteDTO.birthday,
        document = createClienteDTO.document,
        documentType = createClienteDTO.documentType
    )

fun clienteToDTO(cliente: Cliente) =
    ClienteDTO(
        id = cliente.id!!,
        name = cliente.name,
        lastName = cliente.lastName,
        email = cliente.email,
        birthday = cliente.birthday,
        document = cliente.document,
        documentType = cliente.documentType,
        wallet = WalletDTO(
            account = cliente.wallet?.account!!,
            balance = cliente.wallet?.balance!!
        )
    )