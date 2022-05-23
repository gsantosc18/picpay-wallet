package com.picpay.wallet.entity

import com.picpay.wallet.enums.HistoryAction
import javax.persistence.*
import javax.persistence.EnumType.STRING
import javax.persistence.GenerationType.SEQUENCE

@Entity
@Table(name = "history")
data class History(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME)
    val id: Int? = null,
    val wallet: Wallet,
    @Enumerated(STRING)
    val historyAction: HistoryAction
) {
    companion object {
        const val SEQUENCE_NAME = "sq_history"
    }
}
