package dev.gerasch.onlineshop.admin.translations

import dev.gerasch.onlineshop.repositories.translations.Translation
import dev.gerasch.onlineshop.repositories.translations.TranslationMapping
import dev.gerasch.onlineshop.repositories.translations.TranslationRepository
import dev.gerasch.onlineshop.repositories.translations.TranslationType

interface ITranslationService {
    fun getTranslations(entityId: Long, translationType: TranslationType): List<TranslationDTO>
    fun upsertTranslations(
            entityId: Long,
            translationType: TranslationType,
            translations: List<TranslationDTO>
    ): List<TranslationDTO>
}

class TranslationService(val translationRepo: TranslationRepository) : ITranslationService {
    override fun getTranslations(
            entityId: Long,
            translationType: TranslationType
    ): List<TranslationDTO> {
        val databaseResult =
                translationRepo.findTranslationMappingByEntityIdAndTranslationType(
                        entityId,
                        translationType
                )
        if (!databaseResult.isPresent()) {
            return emptyList()
        }
        val translationMapping = databaseResult.get()
        return translationMapping.translations.map {
            TranslationDTO(it.id, it.languageCode, it.text)
        }
    }
    override fun upsertTranslations(
            entityId: Long,
            translationType: TranslationType,
            translations: List<TranslationDTO>
    ): List<TranslationDTO> {
        val databaseResult =
                translationRepo.findTranslationMappingByEntityIdAndTranslationType(
                        entityId,
                        translationType
                )
        var translationMapping: TranslationMapping
        if (!databaseResult.isPresent()) {
            translationMapping = TranslationMapping(null, entityId, translationType)
        } else {
            translationMapping = databaseResult.get()
        }
        val savedTranslationMappingResult =
                updateTranslationMapping(translationMapping, translations)
        return savedTranslationMappingResult.translations.map { mapToDTO(it) }
    }

    private fun updateTranslationMapping(
            translationMapping: TranslationMapping,
            translations: List<TranslationDTO>
    ): TranslationMapping {
        translationMapping.translations = translations.map { mapToEntity(it, translationMapping) }
        return translationRepo.save(translationMapping)
    }

    private fun mapToDTO(translation: Translation): TranslationDTO {
        return TranslationDTO(translation.id, translation.languageCode, translation.text)
    }

    private fun mapToEntity(
            translation: TranslationDTO,
            translationMapping: TranslationMapping
    ): Translation {
        return Translation(
                translation.id,
                translation.languageCode,
                translation.text,
                translationMapping
        )
    }
}
