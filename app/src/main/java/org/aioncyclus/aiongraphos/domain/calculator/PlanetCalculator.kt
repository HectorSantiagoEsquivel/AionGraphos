package org.aioncyclus.aiongraphos.domain.calculator
import org.aioncyclus.aiongraphos.data.ephemeris.AstroEngine
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData
import java.time.Instant

class PlanetCalculator(
    private val engine: AstroEngine
) {

    fun calculate(planet: Planet,contextDate: Instant= Instant.now()): PlanetData
    {
        val planetPosition=engine.calculatePlanetPosition(planet,contextDate)
        val zodiacPosition= ZodiacMapper.fromLongitude(planetPosition.longitude)
        val isRetrograde=planetPosition.speedLongitude<0

        return PlanetData(planet,planetPosition,zodiacPosition,isRetrograde)
    }

}