package org.aioncyclus.aiongraphos.domain.calculator

import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.zodiac.Decan

object DecanMapper {
    private val chaldeanOrder = listOf<Planet>(
        Planet.SATURN,
        Planet.JUPITER,
        Planet.MARS,
        Planet.SUN,
        Planet.VENUS,
        Planet.MERCURY,
        Planet.MOON
    )

    fun decanFromLongitude(longitude: Double): Decan
    {
        var lowerBound = 0
        var planetIndex=2
        for (decan in 1..36)
        {
            if(longitude >= lowerBound && longitude<lowerBound+10)
            {
                return Decan(decan,chaldeanOrder[planetIndex])
            }
            lowerBound+=10
            planetIndex=(planetIndex+1)%7
        }
        error("Invalid longitude: $longitude. The longitude must be lower than 360")
    }
}