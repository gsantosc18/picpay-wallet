package com.picpay.wallet.controller

import com.picpay.wallet.BaseTest
import com.picpay.wallet.clienteMock
import com.picpay.wallet.createClienteDtoMock
import com.picpay.wallet.repository.ClienteRepository
import com.picpay.wallet.updateClientDTOMock
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

class ClienteControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val clienteRepository: ClienteRepository
): BaseTest() {
    companion object {
        const val ENDPOINT = "/cliente"
        const val UPDATE = ENDPOINT+"/{id}"
    }
    @Test
    fun `should create a new client`() {
        mockMvc.post(ENDPOINT) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(createClienteDtoMock())
            }
            .andExpect { status { isCreated() } }
    }

    @Test
    fun `should update client`() {
        clienteRepository.save(clienteMock())
        mockMvc.put(UPDATE, 1) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(updateClientDTOMock())
            }
            .andExpect { status { isOk() } }
            .andDo { print() }
    }

    @Test
    fun `shouldn't update cliente if not exist`() {
        mockMvc.put(UPDATE, 2) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(updateClientDTOMock())
            }
            .andExpect { status { isBadRequest() } }
            .andDo { print() }
    }
}