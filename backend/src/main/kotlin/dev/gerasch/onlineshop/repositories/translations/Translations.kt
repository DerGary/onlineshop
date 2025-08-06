package dev.gerasch.onlineshop.repositories.translations

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

@Entity
@Table(name = "translationMappings")
class TranslationMapping(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        var entityId: Long,
        @Enumerated(EnumType.STRING) var translationType: TranslationType,
        @OneToMany(
                mappedBy = "translationMapping",
                cascade = [CascadeType.ALL],
                fetch = FetchType.LAZY,
                targetEntity = Translation::class
        )
        var translations: List<Translation> = emptyList()
)

enum class TranslationType {
        PRODUCT_TITLE,
        PRODUCT_DESCRIPTION,
        FILE_TITLE,
        MEDIA_TITLE
}

@Entity
@Table(name = "translations")
class Translation(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        var languageCode: String,
        @Lob var text: String,
        @ManyToOne
        @JoinColumn(name = "translationMappingId")
        var translationMapping: TranslationMapping,
)
