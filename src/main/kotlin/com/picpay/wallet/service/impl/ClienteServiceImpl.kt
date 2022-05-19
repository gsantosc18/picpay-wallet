package com.picpay.wallet.service.impl

import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClienteDTO
import com.picpay.wallet.dto.UpdateClienteDTO
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.repository.ClienteRepository
import com.picpay.wallet.service.ClienteService
import com.picpay.wallet.util.clienteToDTO
import com.picpay.wallet.util.createDTOToCliente
import org.springframework.stereotype.Service

@Service
class ClienteServiceImpl(
    private val clienteRepository: ClienteRepository
): ClienteService {
    override fun create(createClienteDTO: CreateClienteDTO): ClienteDTO? {
        val createNewCliente = createDTOToCliente(createClienteDTO)

        val wallet = Wallet(cliente = createNewCliente)
        createNewCliente.wallet = wallet

        return clienteToDTO(clienteRepository.save(createNewCliente))
    }

    override fun update(id: Int, updateClientDTO: UpdateClienteDTO) {
        var updateClient = clienteRepository.findById(id).orElseThrow{NotFoundClientException()}

        updateClient.apply {
            name = updateClientDTO.name
            lastName = updateClientDTO.lastName
            birthday = updateClientDTO.birthday
            document = updateClientDTO.document
            documentType = updateClientDTO.documentType
            email = updateClientDTO.email
        }

        clienteRepository.save(updateClient)
    }
}