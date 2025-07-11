package dev.gerasch.onlineshop.shoppingcart

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "shopping-carts")
class ShoppingCart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
    var userId: Long? = null
}
