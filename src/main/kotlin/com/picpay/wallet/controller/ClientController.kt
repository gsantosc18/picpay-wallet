package com.picpay.wallet.controller

import com.picpay.wallet.controller.api.ClientAPI
import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClientDTO
import com.picpay.wallet.dto.UpdateClientDTO
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.service.ClientService
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
class ClientController(
    private val clientService: ClientService
):ClientAPI {
    override fun createNew(@RequestBody createClientDTO: CreateClientDTO): ResponseEntity<ClienteDTO> =
        status(CREATED).body(clientService.create(createClientDTO))

    override fun update(@PathVariable("id") id: Int, @RequestBody updateClientDTO: UpdateClientDTO) {
        clientService.update(id, updateClientDTO)
    }

    @ExceptionHandler(NotFoundClientException::class)
    fun handlerException(runtimeException: RuntimeException) =
        status(BAD_REQUEST).body(mapOf("message" to runtimeException.message))
}