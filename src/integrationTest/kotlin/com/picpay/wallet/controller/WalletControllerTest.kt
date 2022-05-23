package com.picpay.wallet.controller

import com.picpay.wallet.*
import com.picpay.wallet.dto.WithdrawDTO
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.repository.WalletRepository
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
@SqlGroup(
    Sql(scripts = ["classpath:db/insert_client.sql","classpath:db/insert_wallet.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql(scripts = ["classpath:db/clean_wallet.sql", "classpath:db/clean_client.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
class WalletControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val walletRepository: WalletRepository
): BaseTest() {
    companion object {
        const val ENDPOINT = "/wallet"
        const val WITHDRAWAL = ENDPOINT+"/withdrawal"
        const val TRANSFER = ENDPOINT+"/transfer"
        const val DEPOSIT = ENDPOINT+"/deposit"
    }

    @Test
    fun `should withdrawal from wallet`() {
        val savedWallet = walletRecord(1)
        mockMvc.post(WITHDRAWAL) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(WithdrawDTO(savedWallet.account!!, 5.0))
            }
            .andExpect { status { isOk() } }
            .andDo { print() }
    }

    @Test
    fun `shouldn't withdrawal from wallet`() {
        val savedWallet = walletRecord(1)

        mockMvc.post(WITHDRAWAL) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(WithdrawDTO(savedWallet.account!!, 15.0))
            }
            .andExpect { status { isBadRequest() } }
            .andDo { print() }
    }

    @Test
    fun `shouldn't withdrawal if not found wallet`() {
        mockMvc.post(WITHDRAWAL) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(WithdrawDTO(100, 15.0))
            }
            .andExpect { status { isBadRequest() } }
            .andDo { print() }
    }

    @Test
    fun `should transfer value between wallets`() {
        mockMvc.post(TRANSFER) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(transferDTOMock())
            }
            .andExpect {
                status { isOk() }
                jsonPath("$.balance", Matchers.`is`(5.0))
            }
            .andDo { print() }
    }

    @Test
    fun `shouldn't transfer value between wallets if not enough ballance`() {
        mockMvc.post(TRANSFER) {
            contentType = APPLICATION_JSON
            accept = APPLICATION_JSON
            content = mapper().writeValueAsString(invalidTransferDTOMock())
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.message", Matchers.`is`("A carteira não tem saldo suficiente."))
            }
            .andDo { print() }
    }

    @Test
    fun `shouldn't transfer if wallet not exist`() {
        mockMvc.post(TRANSFER) {
            contentType = APPLICATION_JSON
            accept = APPLICATION_JSON
            content = mapper().writeValueAsString(senderNotExistTransferDTOMock())
        }
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.message", Matchers.`is`("O cliente não foi encontrado."))
            }
            .andDo { print() }
    }

    @Test
    fun `should deposit value in wallet`() {
        mockMvc.post(DEPOSIT) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(depositDTOMock())
            }
            .andExpect {
                status { isOk() }
                jsonPath("$.balance", Matchers.notNullValue())
            }
            .andDo { print() }
    }

    @Test
    fun `shouldn't deposit in wallet if value is negative`() {
        mockMvc.post(DEPOSIT) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(depositDTOValueNegativeMock())
            }
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.message", Matchers.`is`("O valor informa deve ser positivo."))
            }
            .andDo { print() }
    }

    fun walletRecord(id: Int): Wallet {
        return walletRepository.findById(id).orElseThrow { RuntimeException() }
    }
}