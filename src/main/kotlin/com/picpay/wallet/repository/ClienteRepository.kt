package com.picpay.wallet.repository

import com.picpay.wallet.entity.Cliente
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteRepository: CrudRepository<Cliente, Int>