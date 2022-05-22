package com.picpay.wallet.controller

import com.picpay.wallet.BaseTest
import com.picpay.wallet.dto.WithdrawDTO
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.invalidTransferDTOMock
import com.picpay.wallet.repository.WalletRepository
import com.picpay.wallet.senderNotExistTransferDTOMock
import com.picpay.wallet.transferDTOMock
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
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

    fun walletRecord(id: Int): Wallet {
        return walletRepository.findById(id).orElseThrow { RuntimeException() }
    }
}