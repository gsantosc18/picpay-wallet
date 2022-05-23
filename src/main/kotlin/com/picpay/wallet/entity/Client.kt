package com.picpay.wallet.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "client")
class Client (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME)
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
    @OneToOne(mappedBy = "client", cascade = [ CascadeType.ALL ])
    var wallet: Wallet? = null
) {
    companion object {
        const val SEQUENCE_NAME = "sq_client"
    }
}