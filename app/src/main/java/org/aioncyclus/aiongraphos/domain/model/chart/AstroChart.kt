package org.aioncyclus.aiongraphos.domain.model.chart

import org.aioncyclus.aiongraphos.domain.model.house.HousesData
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData

data class AstroChart(
    val planetaryData: List<PlanetData>,
    val housesData: HousesData,
    val chartContext: ChartContext
){
    fun getPlanetData(planet: Planet): PlanetData =
        planetaryData.first { it.planet == planet }
}