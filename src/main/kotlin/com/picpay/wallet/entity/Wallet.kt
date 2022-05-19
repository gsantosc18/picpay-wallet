package com.picpay.wallet.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.ToString
import javax.persistence.*

@Entity
@Table(name = "wallet")
class Wallet (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var account: Int? = null,
    @OneToOne
    @JoinColumn(name = "cliente")
    @JsonIgnore
    @ToString.Exclude
    var cliente: Cliente,
    @Column(name = "balance")
    var balance: Double = 0.0,
)