package org.aioncyclus.aiongraphos.domain.model.chart

import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.lot.LotData
import org.aioncyclus.aiongraphos.domain.model.planet.Condition
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData
import org.aioncyclus.aiongraphos.ui.components.planetdashboard.PlanetCardUIState

data class ChartAnalysis(
    val aspects: List<Aspect>,
    val lots: List<LotData>,
    val sect: Sect,
    val conditions: List<Condition>
)
{
    fun getPlanetCondition(planet: Planet): Int =
        conditions.first { it.planet == planet }.score
    fun getPlanetAspects(planet:Planet): List<Aspect>
    {
        val planetAspects=mutableListOf<Aspect>()
        for (aspect in aspects)
        {
            if(aspect.planetA==planet||aspect.planetB==planet)
            {
                planetAspects.add(aspect)
            }
        }
        return planetAspects
    }
}