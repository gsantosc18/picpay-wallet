package com.picpay.wallet.service.impl

import com.picpay.wallet.exception.DestinationNotFoundException
import com.picpay.wallet.exception.InsuficienteBalanceException
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.repository.WalletRepository
import com.picpay.wallet.secondWalletMock
import com.picpay.wallet.transferDTOMock
import com.picpay.wallet.walletDTOMock
import com.picpay.wallet.walletMock
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyInt
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
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
    fun `should withdrawal from wallet`() {
        given(walletRepository.findById(anyInt())).willReturn(Optional.of(walletMock()))
        val withdrawal = walletServiceImpl.withdrawal(walletDTOMock())

        assertThat(withdrawal.balance).isEqualTo(5.0)
        assertThat(withdrawal.account).isEqualTo(walletDTOMock().account)
    }

    @Test
    fun `shouldn't withdrawal if don't enough ballance`() {
        val walletMock = walletMock()
            walletMock.balance = 1.0
        given(walletRepository.findById(anyInt())).willReturn(Optional.of(walletMock))

        assertThatThrownBy { walletServiceImpl.withdrawal(walletDTOMock()) }
            .isInstanceOf(InsuficienteBalanceException::class.java)
    }

    @Test
    fun `shouldn't withdrawal if not exist client`() {
        val walletMock = walletMock()
        walletMock.balance = 1.0
        given(walletRepository.findById(anyInt())).willReturn(Optional.empty())

        assertThatThrownBy { walletServiceImpl.withdrawal(walletDTOMock()) }
            .isInstanceOf(NotFoundClientException::class.java)
    }

    @Test
    fun `should transfer value between wallets`() {
        val firstWallet = walletMock()
        var secondWalletMock = secondWalletMock()
        val transferDTO = transferDTOMock()
        given(walletRepository.findById(1)).willReturn(Optional.of(firstWallet))
        given(walletRepository.findById(2)).willReturn(Optional.of(secondWalletMock))

        val transfer = walletServiceImpl.transfer(transferDTO)

        firstWallet.balance = 0.0
        secondWalletMock.balance = 15.0

        verify(walletRepository).save(firstWallet)
        verify(walletRepository).save(secondWalletMock)
        assertThat(transfer.balance).isEqualTo(0.0)
        assertThat(transfer.account).isEqualTo(firstWallet.account)
    }

    @Test
    fun `shouldn't transfer value if cliente destination not exist`() {
        val firstWallet = walletMock()

        given(walletRepository.findById(1)).willReturn(Optional.of(firstWallet))
        given(walletRepository.findById(2)).willReturn(Optional.empty())

        assertThatThrownBy { walletServiceImpl.transfer(transferDTOMock()) }
            .isInstanceOf(DestinationNotFoundException::class.java)
    }

    @Test
    fun `shouldn't transfer value if cliente sender not exist`() {
        given(walletRepository.findById(1)).willReturn(Optional.empty())

        assertThatThrownBy { walletServiceImpl.transfer(transferDTOMock()) }
            .isInstanceOf(NotFoundClientException::class.java)
    }

    @Test
    fun `shouldn't transfer value if not have balance enough`() {
        val firstWallet = walletMock()
        var secondWalletMock = secondWalletMock()

        firstWallet.balance = 0.0

        given(walletRepository.findById(1)).willReturn(Optional.of(firstWallet))
        given(walletRepository.findById(2)).willReturn(Optional.of(secondWalletMock))

        assertThatThrownBy { walletServiceImpl.transfer(transferDTOMock()) }
            .isInstanceOf(InsuficienteBalanceException::class.java)
    }
}