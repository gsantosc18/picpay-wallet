package com.picpay.wallet.service

import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClienteDTO
import com.picpay.wallet.dto.UpdateClienteDTO

interface ClienteService {
    fun create(createClienteDTO: CreateClienteDTO): ClienteDTO?
    fun update(id: Int, updateClienteDTO: UpdateClienteDTO)
}