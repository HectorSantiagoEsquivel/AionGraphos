package org.aioncyclus.aiongraphos.domain.model.aspect

import org.aioncyclus.aiongraphos.domain.model.planet.Planet

data class Aspect(
    val planetA: Planet,
    val planetB: Planet,
    val aspectType: AspectType,
    val separation: Distance
)