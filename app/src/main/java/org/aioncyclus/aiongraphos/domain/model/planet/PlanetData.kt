package org.aioncyclus.aiongraphos.domain.model.planet

import org.aioncyclus.aiongraphos.domain.model.zodiac.ZodiacPosition

data class PlanetData(
    val planet: Planet,
    val planetPosition: PlanetPosition,
    val zodiacPosition: ZodiacPosition,
    val isRetrograde: Boolean
)