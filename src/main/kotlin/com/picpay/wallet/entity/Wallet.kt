package com.picpay.wallet.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.ToString
import javax.persistence.*

@Entity
@Table(name = "wallet")
class Wallet (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val account: Int,
    @OneToOne
    @JoinColumn(name = "cliente")
    @JsonIgnore
    @ToString.Exclude
    val cliente: Cliente,
    @Column(name = "balance")
    val balance: Double,
)