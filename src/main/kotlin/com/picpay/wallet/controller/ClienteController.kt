package com.picpay.wallet.controller

import com.picpay.wallet.controller.api.ClienteAPI
import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClienteDTO
import com.picpay.wallet.dto.UpdateClienteDTO
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.service.ClienteService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException

@RestController
class ClienteController(
    private val clienteService: ClienteService
):ClienteAPI {
    override fun createNew(@RequestBody createClienteDTO: CreateClienteDTO): ResponseEntity<ClienteDTO> =
        status(CREATED).body(clienteService.create(createClienteDTO))

    override fun update(@PathVariable("id") id: Int, @RequestBody updateClienteDTO: UpdateClienteDTO) {
        clienteService.update(id, updateClienteDTO)
    }

    @ExceptionHandler(NotFoundClientException::class)
    fun handlerException(runtimeException: RuntimeException) =
        status(BAD_REQUEST).body(mapOf("message" to runtimeException.message))
}