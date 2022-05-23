package com.picpay.wallet.repository

import com.picpay.wallet.entity.History
import org.springframework.data.repository.CrudRepository

interface HistoryRepository: CrudRepository<History, Int>