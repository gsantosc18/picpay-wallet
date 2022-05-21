package com.picpay.wallet.controller.api

import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClienteDTO
import com.picpay.wallet.dto.UpdateClienteDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping


@Api(value = "/", description = "Manipulação do cliente")
@RequestMapping("/cliente")
interface ClienteAPI {
    @ApiOperation(value = "Cria um novo cliente")
    @PostMapping
    fun createNew(createClienteDTO: CreateClienteDTO): ResponseEntity<ClienteDTO>
    @ApiOperation(value = "Atualiza as informações de cliente existente")
    @PutMapping("/{id}")
    fun update(id: Int, updateClienteDTO: UpdateClienteDTO)
}