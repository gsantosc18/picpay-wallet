package com.picpay.wallet.controller

import com.picpay.wallet.BaseTest
import com.picpay.wallet.createClienteDtoMock
import com.picpay.wallet.updateClientDTOMock
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@SqlGroup(
    Sql(scripts = ["classpath:db/insert_client.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql(scripts = ["classpath:db/clean_wallet.sql","classpath:db/clean_client.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
class ClientControllerTest(
    @Autowired private val mockMvc: MockMvc
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
            .andExpect {
                status { isCreated() }
                jsonPath("$.createdAt", Matchers.notNullValue())
            }
            .andDo { print() }
    }

    @Test
    fun `should update client`() {
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
        mockMvc.put(UPDATE, 100) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(updateClientDTOMock())
            }
            .andExpect { status { isBadRequest() } }
            .andDo { print() }
    }
}