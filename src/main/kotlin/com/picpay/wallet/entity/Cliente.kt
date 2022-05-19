package com.picpay.wallet.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "cliente")
class Cliente (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,
    @Column(name = "name")
    val name: String,
    @Column(name = "lastname")
    val lastName: String,
    @Column(name = "email")
    val email: String,
    @Column(name = "birthday")
    val birthday: LocalDate,
    @Column(name = "document")
    val document: String,
    @Enumerated
    @Column(name = "documenttype")
    val documentType: DocumentType,
    @OneToOne(mappedBy = "cliente")
    val wallet: Wallet
)