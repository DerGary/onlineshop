package dev.gerasch.onlineshop.repositories.product

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Basic
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
class Product(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        @OneToMany(
                mappedBy = "product",
                cascade = [CascadeType.ALL],
                fetch = FetchType.EAGER,
                targetEntity = ProductPrice::class
        )
        var prices: List<ProductPrice> = emptyList(),
        @OneToMany(
                mappedBy = "product",
                cascade = [CascadeType.ALL],
                fetch = FetchType.LAZY,
                targetEntity = ProductMedia::class
        )
        var media: List<ProductMedia> = emptyList(),
        @OneToMany(
                mappedBy = "product",
                cascade = [CascadeType.ALL],
                fetch = FetchType.LAZY,
                targetEntity = ProductFile::class
        )
        var files: List<ProductFile> = emptyList()
        var isBuyable: Boolean
)

@Entity
@Table(name = "productMedia")
class ProductMedia(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        @ManyToOne @JoinColumn(name = "productId") var product: Product,
        var order: UInt,
        var mediaExtension: String,
        @Lob @Basic(fetch = FetchType.LAZY) var data: ByteArray,
)

@Entity
@Table(name = "productFiles")
class ProductFile(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        @ManyToOne @JoinColumn(name = "productId") var product: Product,
        @Lob @Basic(fetch = FetchType.LAZY) var data: ByteArray,
)

@Entity
@Table(name = "productPrices")
class ProductPrice(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        @ManyToOne @JoinColumn(name = "productId") var product: Product,
        var amount: BigDecimal,
        var validFrom: LocalDateTime,
        var validTo: LocalDateTime,
        @Enumerated(EnumType.STRING) var priceType: PriceType,
)

enum class PriceType {
        DISCOUNTED,
        UVP
}
