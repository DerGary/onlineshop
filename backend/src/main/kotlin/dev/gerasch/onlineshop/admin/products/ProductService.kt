package dev.gerasch.onlineshop.admin.products

import dev.gerasch.onlineshop.admin.translations.ITranslationService
import dev.gerasch.onlineshop.repositories.product.Product
import dev.gerasch.onlineshop.repositories.product.ProductFile
import dev.gerasch.onlineshop.repositories.product.ProductMedia
import dev.gerasch.onlineshop.repositories.product.ProductMediaRepository
import dev.gerasch.onlineshop.repositories.product.ProductFileRepository 
import dev.gerasch.onlineshop.repositories.product.ProductPrice
import dev.gerasch.onlineshop.repositories.product.ProductRepository
import dev.gerasch.onlineshop.repositories.translations.TranslationType
import java.time.LocalDateTime

interface IProductService {
    fun upsertProduct(product: ProductDTO): ProductDTO
    fun deleteProduct(productId: Long)
    fun upsertMedia(
            productId: Long,
            productMedia: ProductMediaDTO,
            data: ByteArray
    ): ProductMediaDTO
    fun deleteMedia(productId: Long, mediaId: Long)
    fun upsertFile(productId: Long, productFile: ProductFileDTO, data: ByteArray): ProductFileDTO
    fun deleteFile(productId: Long, fileId: Long)
}

class ProductService(
        val productRepo: ProductRepository,
        val translationService: ITranslationService,
        val productMediaRepo: ProductMediaRepository
        val productFileRepo: ProductFileRepository
) : IProductService {
    override fun upsertProduct(product: ProductDTO): ProductDTO {
        val productToSave = mapToProduct(product)
        var savedProduct: Product
        if (productToSave.id != null) {
            savedProduct = updateProduct(productToSave)
        } else {
            savedProduct = createProduct(productToSave)
        }
        translationService.upsertTranslations(
                savedProduct.id!!,
                TranslationType.PRODUCT_TITLE,
                product.titles
        )
        translationService.upsertTranslations(
                savedProduct.id!!,
                TranslationType.PRODUCT_DESCRIPTION,
                product.descriptions
        )
        return mapToProductDTO(savedProduct)
    }

    override fun upsertMedia(
            productId: Long,
            productMedia: ProductMediaDTO,
            data: ByteArray
    ): ProductMediaDTO {
        val product = getProductWithGuard(productId)
        val productMediaToSave = mapToProductMedia(product, productMedia, data)
        val savedProductMedia = productMediaRepo.save(productMediaToSave)
        translationService.upsertTranslations(
                savedProductMedia.id!!,
                TranslationType.MEDIA_TITLE,
                productMedia.titles
        )
        return mapToProductMediaDTO(savedProductMedia)
    }

    override fun upsertFile(
            productId: Long,
            productFile: ProductFileDTO,
            data: ByteArray
    ): ProductFileDTO {
        val product = getProductWithGuard(productId)
        val productFileToSave = mapToProductFile(product, productFile, data)
        val savedProductFile = productFileRepo.save(productFileToSave)
        translationService.upsertTranslations(
                savedProductFile.id!!,
                TranslationType.FILE_TITLE,
                productFile.titles
        )
        return mapToProductFileDTO(savedProductFile)
    }

    private fun getProductWithGuard(productId: Long): Product {
        val databaseResult = productRepo.findById(productId)
        if (databaseResult.isEmpty()) {
            throw ProductNotFoundException("The product with id ${productId} could not be found")
        }
        return databaseResult.get()
    }

    private fun mapToProductMedia(
            product: Product,
            productMedia: ProductMediaDTO,
            data: ByteArray
    ): ProductMedia {
        return ProductMedia(
                productMedia.id,
                product,
                productMedia.order,
                productMedia.mediaExtension,
                data
        )
    }

    private fun mapToProductMediaDTO(productMedia: ProductMedia): ProductMediaDTO {
        return ProductMediaDTO(
                productMedia.id,
                productMedia.order,
                translationService.getTranslations(
                        productMedia.product.id!!,
                        TranslationType.MEDIA_TITLE
                ),
                productMedia.mediaExtension,
                RawLink("/products/${productMedia.product.id}/media/${productMedia.id}/raw")
        )
    }

    private fun mapToProductFile(
            product: Product,
            productFile: ProductFileDTO,
            data: ByteArray
    ): ProductFile {
        return ProductFile(productFile.id, product, data)
    }

    private fun mapToProductFileDTO(productFile: ProductFile): ProductFileDTO {
        return ProductFileDTO(
                productFile.id,
                translationService.getTranslations(
                        productFile.product.id!!,
                        TranslationType.FILE_TITLE
                ),
                RawLink("/products/${productFile.product.id}/files/${productFile.id}/raw")
        )
    }

    private fun mapToProduct(product: ProductDTO): Product {
        val mappedProduct = Product(product.id, isBuyable = product.isBuyable)
        mappedProduct.prices =
                product.prices.map {
                    ProductPrice(
                            it.id,
                            mappedProduct,
                            it.amount,
                            it.validFrom ?: LocalDateTime.MIN,
                            it.validTo ?: LocalDateTime.MAX,
                            it.priceType
                    )
                }
        return mappedProduct
    }

    private fun mapToProductDTO(product: Product): ProductDTO {
        val productId = product.id!!
        val mappedProduct = ProductDTO(productId, isBuyable = product.isBuyable)
        mappedProduct.prices =
                product.prices.map {
                    ProductPriceDTO(it.id, it.amount, it.validFrom, it.validTo, it.priceType)
                }
        mappedProduct.media = product.media.map { mapToProductMediaDTO(it) }
        mappedProduct.files = product.files.map { mapToProductFileDTO(it) }
        mappedProduct.titles =
                translationService.getTranslations(productId, TranslationType.PRODUCT_TITLE)
        mappedProduct.descriptions =
                translationService.getTranslations(productId, TranslationType.PRODUCT_DESCRIPTION)
        return mappedProduct
    }

    private fun updateProduct(product: Product): Product {
        val databaseResult = productRepo.findById(product.id!!)
        if (!databaseResult.isPresent()) {
            return createProduct(product)
        }
        val savedProduct = databaseResult.get()
        savedProduct.prices = product.prices
        savedProduct.isBuyable = product.isBuyable
        return productRepo.save(savedProduct)
    }

    private fun createProduct(product: Product): Product {
        return productRepo.save(product)
    }

    override fun deleteMedia(productId: Long, mediaId: Long){
        if(!productRepo.existsById(productId)){
            throw ProductNotFoundException("The product with id ${productId} could not be found")
        }
        productMediaRepo.deleteById(mediaId)
    }

    override fun deleteFile(productId: Long, fileId: Long){
        if(!productRepo.existsById(productId)){
            throw ProductNotFoundException("The product with id ${productId} could not be found")
        }
        productFileRepo.deleteById(fileId)
    }

    override fun deleteProduct(productId: Long){
        productRepo.deleteById(productId)
    }
}
