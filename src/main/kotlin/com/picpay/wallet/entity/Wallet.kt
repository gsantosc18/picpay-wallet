package com.picpay.wallet.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.picpay.wallet.dto.HistoryDTO
import lombok.ToString
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "wallet")
class Wallet (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME)
    var account: Int? = null,
    @OneToOne
    @JoinColumn(name = "cliente")
    @JsonIgnore
    @ToString.Exclude
    var client: Client,
    @Column(name = "balance")
    var balance: Double = 0.0,
    @OneToMany(mappedBy = "wallet")
    var historys: MutableList<History> = mutableListOf<History>(),
    override var createdAt: LocalDateTime? = null
): BaseEntity() {
    companion object {
        const val SEQUENCE_NAME = "sq_wallet"
    }

    fun addHistory(history: History) {
        historys += history
    }
}