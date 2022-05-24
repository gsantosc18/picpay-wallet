package com.picpay.wallet.entity

import com.fasterxml.jackson.annotation.JsonIgnore
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
    override var createdAt: LocalDateTime? = null
): BaseEntity() {
    companion object {
        const val SEQUENCE_NAME = "sq_wallet"
    }

    @OneToMany(mappedBy = "wallet")
    private var historys = mutableListOf<History>()

    fun addHistory(history: History) {
        historys += history
    }
}