package com.picpay.wallet.repository

import com.picpay.wallet.entity.Wallet
import org.springframework.data.repository.CrudRepository

interface WalletRepository: CrudRepository<Wallet, Int>