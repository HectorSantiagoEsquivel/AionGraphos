package org.aioncyclus.aiongraphos.domain.model.house

import org.aioncyclus.aiongraphos.domain.model.zodiac.ZodiacPosition

data class House(
    val number: Int,
    val cusp: Double,
    val zodiacPosition: ZodiacPosition
)