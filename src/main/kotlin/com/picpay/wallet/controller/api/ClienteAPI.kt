package com.picpay.wallet.controller.api

import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClienteDTO
import com.picpay.wallet.dto.UpdateClienteDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/cliente")
interface ClienteAPI {
    @PostMapping
    fun createNew(createClienteDTO: CreateClienteDTO): ResponseEntity<ClienteDTO>
    @PutMapping("/{id}")
    fun update(id: Int, updateClienteDTO: UpdateClienteDTO)
}