package com.picpay.wallet.service

import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClienteDTO

interface ClienteService {
    fun create(createClienteDTO: CreateClienteDTO): ClienteDTO?
}