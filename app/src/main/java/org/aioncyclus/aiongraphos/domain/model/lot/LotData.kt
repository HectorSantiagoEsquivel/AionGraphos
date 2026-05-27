package org.aioncyclus.aiongraphos.domain.model.lot

import org.aioncyclus.aiongraphos.domain.model.zodiac.ZodiacPosition

data class LotData(
    val lotType: LotType,
    val longitude: Double,
    val zodiacPosition: ZodiacPosition,
)