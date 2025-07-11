package dev.gerasch.onlineshop.shoppingcart

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "shoppingcarts", path = "shoppingcarts")
interface ShoppingCartRepository :
        PagingAndSortingRepository<ShoppingCart, Long>, CrudRepository<ShoppingCart, Long> {}
