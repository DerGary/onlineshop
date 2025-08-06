package dev.gerasch.onlineshop.repositories.translations

import java.util.Optional
import org.springframework.data.repository.CrudRepository

interface TranslationRepository : CrudRepository<TranslationMapping, Long> {
    fun findTranslationMappingByEntityIdAndTranslationType(
            entityId: Long,
            translationType: TranslationType
    ): Optional<TranslationMapping>
}
