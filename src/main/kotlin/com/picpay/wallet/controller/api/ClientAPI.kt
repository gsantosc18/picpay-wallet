package com.picpay.wallet.controller.api

import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClientDTO
import com.picpay.wallet.dto.UpdateClientDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping


@Api(value = "/", description = "Manipulação do cliente")
@RequestMapping("/cliente")
interface ClientAPI {
    @ApiOperation(value = "Criar um novo cliente")
    @PostMapping
    fun createNew(createClientDTO: CreateClientDTO): ResponseEntity<ClienteDTO>
    @ApiOperation(value = "Atualizar as informações de cliente existente")
    @PutMapping("/{id}")
    fun update(id: Int, updateClientDTO: UpdateClientDTO)
}