package com.picpay.wallet.controller

import com.picpay.wallet.BaseTest
import com.picpay.wallet.clienteMock
import com.picpay.wallet.dto.WithdrawDTO
import com.picpay.wallet.entity.Wallet
import com.picpay.wallet.repository.ClienteRepository
import com.picpay.wallet.repository.WalletRepository
import com.picpay.wallet.walletMock
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

class WalletControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val walletRepository: WalletRepository,
    @Autowired private val clienteRepository: ClienteRepository
): BaseTest() {
    companion object {
        const val ENDPOINT = "/wallet"
    }

    @Test
    fun `should withdrawal from wallet`() {
        val savedWallet = walletRecord()
        mockMvc.post(ENDPOINT) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(WithdrawDTO(savedWallet.account!!, 5.0))
            }
            .andExpect { status { isOk() } }
            .andDo { print() }
    }

    @Test
    fun `shouldn't withdrawal from wallet`() {
        val savedWallet = walletRecord()

        mockMvc.post(ENDPOINT) {
                contentType = APPLICATION_JSON
                accept = APPLICATION_JSON
                content = mapper().writeValueAsString(WithdrawDTO(savedWallet.account!!, 15.0))
            }
            .andExpect { status { isBadRequest() } }
            .andDo { print() }
    }

    @Test
    fun `shouldn't withdrawal if not found wallet`() {

        mockMvc.post(ENDPOINT) {
            contentType = APPLICATION_JSON
            accept = APPLICATION_JSON
            content = mapper().writeValueAsString(WithdrawDTO(100, 15.0))
        }
            .andExpect { status { isBadRequest() } }
            .andDo { print() }
    }

    fun walletRecord(): Wallet {
        val client = clienteRepository.save(clienteMock())
        val wallet = walletMock(client)
        return walletRepository.save(wallet)
    }
}