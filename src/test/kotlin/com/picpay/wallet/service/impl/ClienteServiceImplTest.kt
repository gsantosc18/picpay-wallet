package com.picpay.wallet.service.impl

import com.picpay.wallet.clienteMock
import com.picpay.wallet.createClienteDtoMock
import com.picpay.wallet.exception.NotFoundClientException
import com.picpay.wallet.repository.ClienteRepository
import com.picpay.wallet.updateClientDTOMock
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

internal class ClienteServiceImplTest {
    @InjectMocks
    lateinit var clienteServiceImpl: ClienteServiceImpl

    @Mock
    lateinit var clienteRepository: ClienteRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should create a new client`() {
        given(clienteRepository.save(any())).willReturn(clienteMock())
        val create = clienteServiceImpl.create(createClienteDtoMock())

        assertThat(create).isNotNull
        assertThat(create!!.id).isEqualTo(1)
    }

    @Test
    fun `should update client`() {
        given(clienteRepository.findById(any())).willReturn(Optional.of(clienteMock()))

        clienteServiceImpl.update(1, updateClientDTOMock())

        verify(clienteRepository).save(any())
    }

    @Test
    fun `shouldn't update client if not exist`() {
        given(clienteRepository.findById(any())).willReturn(Optional.empty())

        assertThatThrownBy { clienteServiceImpl.update(1, updateClientDTOMock()) }.isInstanceOf(NotFoundClientException::class.java)
    }
}