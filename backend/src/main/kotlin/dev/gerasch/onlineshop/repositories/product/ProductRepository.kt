package dev.gerasch.onlineshop.repositories.product

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface ProductRepository :
        PagingAndSortingRepository<Product, Long>, CrudRepository<Product, Long> {}

interface ProductMediaRepository : CrudRepository<ProductMedia, Long>

interface ProductFileRepository : CrudRepository<ProductFile, Long>
