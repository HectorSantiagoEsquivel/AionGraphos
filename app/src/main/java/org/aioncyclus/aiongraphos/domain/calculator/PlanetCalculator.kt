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

    fun calculateNodeData(isTrueNode: Boolean, contextDate: Instant=Instant.now()): List<PlanetData>
    {

        val northNode=if(isTrueNode) Planet.TRUE_NORTH_NODE
        else Planet.MEAN_NORTH_NODE


        val southNode = when(northNode) {
            Planet.MEAN_NORTH_NODE -> Planet.MEAN_SOUTH_NODE
            Planet.TRUE_NORTH_NODE -> Planet.TRUE_SOUTH_NODE
            else -> throw IllegalArgumentException(
                "calculateNodeData expects a north node, $northNode given instead"
            )
        }

        val northNodePosition=engine.calculatePlanetPosition(northNode,contextDate)
        val southNodePosition = northNodePosition.copy(
            longitude = ZodiacMapper.normaliseLongitude(
                northNodePosition.longitude + 180
            )
        )

        val northZodiacPosition= ZodiacMapper.fromLongitude(northNodePosition.longitude)
        val southZodiacPosition= ZodiacMapper.fromLongitude(southNodePosition.longitude)

        val isRetrograde=northNodePosition.speedLongitude<0

       return listOf<PlanetData>(PlanetData(northNode,northNodePosition,northZodiacPosition,isRetrograde),
           PlanetData(southNode,southNodePosition,southZodiacPosition,isRetrograde))

    }

}