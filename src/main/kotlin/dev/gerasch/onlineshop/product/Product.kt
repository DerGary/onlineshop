package dev.gerasch.onlineshop.product

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
data class Product(val name: String, val price: Double) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
}
