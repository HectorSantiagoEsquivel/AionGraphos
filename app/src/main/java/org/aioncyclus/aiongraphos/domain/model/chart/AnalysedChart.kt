package org.aioncyclus.aiongraphos.domain.model.chart

import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.planet.Condition
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData

data class AnalysedChart(
    val chart: AstroChart,
    val analysis: ChartAnalysis
){
    fun getPlanetData(planet: Planet): PlanetData =
        chart.getPlanetData(planet)
    fun getNodeDAta(node: Planet): PlanetData=
        chart.getNodeDAta(node)
    fun getPlanetCondition(planet: Planet): Int =
        analysis.getPlanetCondition(planet)
    fun getPlanetAspects(planet: Planet): List<Aspect> =
        analysis.getPlanetAspects(planet)
}
