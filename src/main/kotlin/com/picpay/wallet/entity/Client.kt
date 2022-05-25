package com.picpay.wallet.entity

import com.picpay.wallet.enums.DocumentType
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.EnumType.STRING

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
    @Enumerated(STRING)
    @Column(name = "documenttype")
    var documentType: DocumentType,
    @OneToOne(mappedBy = "client", cascade = [ CascadeType.ALL ])
    var wallet: Wallet? = null,
    override var createdAt: LocalDateTime? = null
): BaseEntity() {
    companion object {
        const val SEQUENCE_NAME = "sq_client"
    }
}