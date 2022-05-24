package com.picpay.wallet.service.impl

import com.picpay.wallet.dto.ClienteDTO
import com.picpay.wallet.dto.CreateClientDTO
import com.picpay.wallet.dto.UpdateClientDTO
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.repository.ClienteRepository
import com.picpay.wallet.service.ClientService
import com.picpay.wallet.util.clientToDTO
import com.picpay.wallet.util.createDTOToClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl(
    private val clienteRepository: ClienteRepository
): ClientService {
    private val log = LoggerFactory.getLogger(ClientServiceImpl::class.java)
    override fun create(createClientDTO: CreateClientDTO): ClienteDTO? {
        log.info("M=create, message=Init creation a new client")
        val createNewCliente = createDTOToClient(createClientDTO)

        val wallet = Wallet(client = createNewCliente)
        createNewCliente.wallet = wallet

        return clientToDTO(clienteRepository.save(createNewCliente))
    }

    override fun update(id: Int, updateClientDTO: UpdateClientDTO) {
        log.info("M=update, message=Init update client, client={}", id)
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
        log.info("M=update, message=Update apply successfully, client={}", id)
    }
}