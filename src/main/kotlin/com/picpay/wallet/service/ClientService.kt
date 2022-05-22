package com.picpay.wallet.service

import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClientDTO
import com.picpay.wallet.dto.UpdateClientDTO

interface ClientService {
    fun create(createClientDTO: CreateClientDTO): ClienteDTO?
    fun update(id: Int, updateClientDTO: UpdateClientDTO)
}