package dev.gerasch.onlineshop.shoppingcart

import java.util.Optional
import org.springframework.data.repository.CrudRepository

interface ShoppingCartRepository : CrudRepository<ShoppingCart, Long> {
    override fun findById(id: Long): Optional<ShoppingCart>
}
