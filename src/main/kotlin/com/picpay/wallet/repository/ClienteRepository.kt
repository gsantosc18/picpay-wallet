package com.picpay.wallet.repository

import com.picpay.wallet.entity.Client
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteRepository: CrudRepository<Client, Int>