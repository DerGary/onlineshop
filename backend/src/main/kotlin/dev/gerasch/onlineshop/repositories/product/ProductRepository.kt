package dev.gerasch.onlineshop.product

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
interface ProductRepository :
        PagingAndSortingRepository<Product, Long>, CrudRepository<Product, Long> {}
