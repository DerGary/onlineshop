package dev.gerasch.onlineshop.admin.products

import dev.gerasch.onlineshop.admin.translations.TranslationDTO
import dev.gerasch.onlineshop.repositories.product.PriceType
import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductDTO(
        var id: Long? = null,
        var prices: List<ProductPriceDTO> = emptyList(),
        var media: List<ProductMediaDTO> = emptyList(),
        var files: List<ProductFileDTO> = emptyList(),
        var isBuyable: Boolean,
        var titles: List<TranslationDTO> = emptyList(),
        var descriptions: List<TranslationDTO> = emptyList()
)

data class ProductPriceDTO(
        var id: Long? = null,
        var amount: BigDecimal,
        var validFrom: LocalDateTime?,
        var validTo: LocalDateTime?,
        var priceType: PriceType
)

data class ProductMediaDTO(
        var id: Long? = null,
        var order: UInt,
        var titles: List<TranslationDTO>,
        var mediaExtension: String,
        var _links: RawLink
)

data class ProductFileDTO(
        var id: Long? = null,
        var titles: List<TranslationDTO> = emptyList(),
        var _links: RawLink
)

class RawLink(href: String) {
        val raw: Link
        init {
                raw = Link(href)
        }
}

data class Link(var href: String)
