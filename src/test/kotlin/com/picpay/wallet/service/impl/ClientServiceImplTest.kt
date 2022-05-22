package com.picpay.wallet.service.impl

import com.picpay.wallet.clienteMock
import com.picpay.wallet.createClienteDtoMock
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.repository.ClienteRepository
import com.picpay.wallet.updateClientDTOMock
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

internal class ClientServiceImplTest {
    @InjectMocks
    lateinit var clientServiceImpl: ClientServiceImpl

    @Mock
    lateinit var clientRepository: ClienteRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should create a new client`() {
        given(clientRepository.save(any())).willReturn(clienteMock())
        val create = clientServiceImpl.create(createClienteDtoMock())

        assertThat(create).isNotNull
        assertThat(create!!.id).isEqualTo(1)
    }

    @Test
    fun `should update client`() {
        given(clientRepository.findById(any())).willReturn(Optional.of(clienteMock()))

        clientServiceImpl.update(1, updateClientDTOMock())

        verify(clientRepository).save(any())
    }

    @Test
    fun `shouldn't update client if not exist`() {
        given(clientRepository.findById(any())).willReturn(Optional.empty())

        assertThatThrownBy { clientServiceImpl.update(1, updateClientDTOMock()) }.isInstanceOf(NotFoundClientException::class.java)
    }
}