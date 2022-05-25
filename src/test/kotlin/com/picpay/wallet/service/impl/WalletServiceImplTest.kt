package com.picpay.wallet.service.impl

import com.picpay.wallet.*
import com.picpay.wallet.enums.HistoryAction.*
import com.picpay.wallet.exception.DestinationNotFoundException
import com.picpay.wallet.exception.InsuficienteBalanceException
import com.picpay.wallet.exception.InvalidValueException
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.rabbit.HistoryProducer
import com.picpay.wallet.repository.WalletRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
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

    @Mock
    lateinit var historyProducer: HistoryProducer

    @Mock
    private lateinit var historyServiceImpl: HistoryServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should withdrawal from wallet`() {
        val walletMock = walletMock()
        given(walletRepository.findById(anyInt())).willReturn(Optional.of(walletMock))
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

    @Test
    fun `should deposit value in wallet`() {
        val walletMock = walletMock()
        given(walletRepository.findById(any())).willReturn(Optional.of(walletMock))
        given(walletRepository.save(any())).willReturn(walletDepositMock())

        var deposit = walletServiceImpl.deposit(depositDTOMock())

        assertThat(deposit.balance).isEqualTo(15.0)
    }

    @Test
    fun `shouldn't deposit if account not exist`() {
        given(walletRepository.findById(any())).willReturn(Optional.empty())
        assertThatThrownBy { walletServiceImpl.deposit(depositDTOMock()) }.isInstanceOf(NotFoundClientException::class.java)
    }

    @Test
    fun `should pay debit from wallet`() {
        val walletMock = walletMock()
        given(walletRepository.findById(any())).willReturn(Optional.of(walletMock))
        given(walletRepository.save(any())).willReturn(walletDepositMock())

        val payDebit = walletServiceImpl.payDebit(payDebitDTOMock())

        assertThat(payDebit.balance).isEqualTo(5.0)
    }

    @Test
    fun `shouldn't pay debit if wallet not exist`() {
        given(walletRepository.findById(any())).willReturn(Optional.empty())
        assertThatThrownBy { walletServiceImpl.payDebit(payDebitDTOMock()) }.isInstanceOf(NotFoundClientException::class.java)
    }

    @Test
    fun `shouldn't pay debit if value is negative`() {
        given(walletRepository.findById(any())).willReturn(Optional.empty())
        assertThatThrownBy { walletServiceImpl.payDebit(payDebitDTONegativeMock()) }.isInstanceOf(InvalidValueException::class.java)
    }
}