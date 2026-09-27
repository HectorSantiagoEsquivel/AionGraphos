package org.aioncyclus.aiongraphos.domain.model.zodiac

import org.aioncyclus.aiongraphos.domain.model.planet.Planet

data class Decan(
    val number: Int,
    val ruler: Planet
)
