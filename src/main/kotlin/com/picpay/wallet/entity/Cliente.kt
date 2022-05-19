package com.picpay.wallet.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "cliente")
class Cliente (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,
    @Column(name = "name")
    var name: String,
    @Column(name = "lastname")
    var lastName: String,
    @Column(name = "email")
    var email: String,
    @Column(name = "birthday")
    var birthday: LocalDate,
    @Column(name = "document")
    var document: String,
    @Enumerated
    @Column(name = "documenttype")
    var documentType: DocumentType,
    @OneToOne(mappedBy = "cliente")
    var wallet: Wallet? = null
)