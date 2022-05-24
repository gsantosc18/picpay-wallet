package com.picpay.wallet.entity

import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
abstract class BaseEntity{
    open var createdAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null

    @PrePersist
    fun prePersist() {
        createdAt = now()
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = now()
    }
}