package com.picpay.wallet.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.picpay.wallet.enums.HistoryAction
import lombok.ToString
import java.time.LocalDateTime
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
    @ManyToOne
    @JoinColumn(name = "wallet")
    @JsonIgnore
    val wallet: Wallet,
    @Enumerated(STRING)
    val historyAction: HistoryAction,
    override var createdAt: LocalDateTime? = null
): BaseEntity() {
    companion object {
        const val SEQUENCE_NAME = "sq_history"
    }
}
