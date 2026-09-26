package org.aioncyclus.aiongraphos.domain.model.dignity.scoreRange

import org.aioncyclus.aiongraphos.domain.model.planet.Planet

object TraditionalPlanetScoreRange {

    fun scoreRangeOf(planet: Planet): PlanetScoreRange
    {
        return when(planet)
        {
            Planet.MOON -> PlanetScoreRange(35,-32)
            Planet.MERCURY -> PlanetScoreRange(44,-36)
            Planet.VENUS -> PlanetScoreRange(33, -36)
            Planet.SUN -> PlanetScoreRange(37,-29)
            Planet.MARS -> PlanetScoreRange(42,-32)
            Planet.JUPITER -> PlanetScoreRange(37,-36)
            Planet.SATURN -> PlanetScoreRange(43,-28)
            else -> PlanetScoreRange(30,-34)
        }
    }
}