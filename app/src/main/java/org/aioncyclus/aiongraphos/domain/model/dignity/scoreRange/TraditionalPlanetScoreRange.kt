package org.aioncyclus.aiongraphos.domain.model.dignity.scoreRange

import org.aioncyclus.aiongraphos.domain.model.planet.Planet

object TraditionalPlanetScoreRange {

    fun scoreRangeOf(planet: Planet): PlanetScoreRange
    {
        return when(planet)
        {
            Planet.SUN -> PlanetScoreRange(37,-29)
            Planet.MOON -> PlanetScoreRange(35,-32)
            Planet.MERCURY -> PlanetScoreRange(44,-36)
            else -> PlanetScoreRange(33,-36)
        }
    }
}