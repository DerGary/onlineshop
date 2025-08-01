package dev.gerasch.onlineshop.repositories.prices

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "countryPriceRules")
class CountryPriceRule(
        @Id val countryCode: String, // "DE", "FR", "US"
        var currency: String,
        var conversionMultiplier: BigDecimal,
        var roundingTo: BigDecimal,
        var taxRate: BigDecimal,
)
