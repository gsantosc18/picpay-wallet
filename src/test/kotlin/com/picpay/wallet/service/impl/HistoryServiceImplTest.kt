package com.picpay.wallet.service.impl

import com.picpay.wallet.enums.HistoryAction
import com.picpay.wallet.historyMock
import com.picpay.wallet.rabbit.HistoryProducer
import com.picpay.wallet.repository.HistoryRepository
import com.picpay.wallet.walletMock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class HistoryServiceImplTest {
    @InjectMocks
    lateinit var historyServiceImpl: HistoryServiceImpl

    @Mock
    lateinit var historyRepository: HistoryRepository

    @Mock
    lateinit var historyProducer: HistoryProducer

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should record movement history`() {
        given(historyRepository.save(any())).willReturn(historyMock())
        historyServiceImpl.save(walletMock(), HistoryAction.WITHDRAWAL)
        verify(historyRepository).save(any())
    }
}