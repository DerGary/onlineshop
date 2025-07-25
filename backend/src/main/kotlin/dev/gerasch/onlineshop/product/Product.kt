package dev.gerasch.onlineshop.product

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product(
        var name: String,
        var description: String,
        var price: Double,
        @OneToMany(
                mappedBy = "product",
                cascade = [CascadeType.ALL],
                fetch = FetchType.LAZY,
                targetEntity = ProductPicture::class
        )
        var pictures: List<ProductPicture> = emptyList(),
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)

@Entity
@Table(name = "productpictures")
class ProductPicture(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        @ManyToOne @JoinColumn(name = "product_id") var product: Product
)
