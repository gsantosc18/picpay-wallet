package com.picpay.wallet.service.impl

import com.picpay.wallet.dto.WithdrawDTO
import com.picpay.wallet.exception.InsuficienteBalanceException
import com.picpay.wallet.repository.WalletRepository
import com.picpay.wallet.walletDTOMock
import com.picpay.wallet.walletMock
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito
import org.mockito.BDDMockito.anyInt
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

internal class WalletServiceImplTest {
    @InjectMocks
    private lateinit var walletServiceImpl: WalletServiceImpl

    @Mock
    private lateinit var walletRepository: WalletRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should withdrawal between wallets`() {
        given(walletRepository.findById(anyInt())).willReturn(Optional.of(walletMock()))
        val withdrawal = walletServiceImpl.withdrawal(walletDTOMock())

        assertThat(withdrawal.value).isEqualTo(5.0)
        assertThat(withdrawal.account).isEqualTo(walletDTOMock().account)
    }

    @Test
    fun `shouldn't withdrawal if don't enough ballance`() {
        var walletMock = walletMock()
            walletMock.balance = 1.0
        given(walletRepository.findById(anyInt())).willReturn(Optional.of(walletMock))

        assertThatThrownBy { walletServiceImpl.withdrawal(walletDTOMock()) }
            .isInstanceOf(InsuficienteBalanceException::class.java)
    }
}