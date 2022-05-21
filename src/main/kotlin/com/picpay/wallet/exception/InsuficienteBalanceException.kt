package com.picpay.wallet.exception

import java.lang.RuntimeException

class InsuficienteBalanceException: RuntimeException("A carteira não tem saldo suficiente.")