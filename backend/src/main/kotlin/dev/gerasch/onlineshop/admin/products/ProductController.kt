package dev.gerasch.onlineshop.admin.products

import jakarta.websocket.server.PathParam
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/admin")
class AdminProductController(val productService: IProductService) {
        @GetMapping("/products")
        fun getProducts(
                @RequestParam("pageNumber") pageNumber: Int = 0,
                @RequestParam("sort") sort: Direction = Direction.DESC
        ): Page<ProductDTO> {
                return productService.getProducts(PageRequest.of(pageNumber, 10, sort, "id"))
        }

        @GetMapping("/products/{productId}")
        fun getProduct(@PathParam("productId") productId: Long): ProductDTO {
                return productService.getProduct(productId)
        }

        @PostMapping("/products")
        fun createProduct(product: ProductDTO): ProductDTO {
                product.id = null
                return productService.upsertProduct(product)
        }

        @PutMapping("/products/{productId}")
        fun updateProduct(
                @PathParam("productId") productId: Long,
                product: ProductDTO
        ): ProductDTO {
                product.id = productId
                return productService.upsertProduct(product)
        }

        @DeleteMapping("/products/{productId}")
        fun deleteProduct(@PathParam("productId") productId: Long) {
                return productService.deleteProduct(productId)
        }

        @PostMapping("/products/{productId}/media")
        fun createProductMedia(
                @PathParam("productId") productId: Long,
                productMedia: ProductMediaDTO,
                data: ByteArray
        ): ProductMediaDTO {
                return productService.upsertMedia(productId, productMedia, data)
        }

        @PutMapping("/products/{productId}/media/{mediaId}")
        fun updateProductMedia(
                @PathParam("productId") productId: Long,
                @PathParam("mediaId") mediaId: Long,
                productMedia: ProductMediaDTO,
                data: ByteArray
        ): ProductMediaDTO {
                productMedia.id = mediaId
                return productService.upsertMedia(productId, productMedia, data)
        }

        @DeleteMapping("/products/{productId}/media/{mediaId}")
        fun deleteProductMedia(
                @PathParam("productId") productId: Long,
                @PathParam("mediaId") mediaId: Long
        ) {
                productService.deleteMedia(productId, mediaId)
        }

        @PostMapping("/products/{productId}/file")
        fun createProductFile(
                @PathParam("productId") productId: Long,
                productFile: ProductFileDTO,
                data: ByteArray
        ): ProductFileDTO {
                return productService.upsertFile(productId, productFile, data)
        }

        @PutMapping("/products/{productId}/file/{fileId}")
        fun updateProductFile(
                @PathParam("productId") productId: Long,
                @PathParam("fileId") fileId: Long,
                productFile: ProductFileDTO,
                data: ByteArray
        ): ProductFileDTO {
                productFile.id = fileId
                return productService.upsertFile(productId, productFile, data)
        }

        @DeleteMapping("/products/{productId}/file/{fileId}")
        fun deleteProductFile(
                @PathParam("productId") productId: Long,
                @PathParam("fileId") fileId: Long
        ) {
                productService.deleteFile(productId, fileId)
        }
}
