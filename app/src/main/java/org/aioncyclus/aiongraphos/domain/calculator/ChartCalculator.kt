package org.aioncyclus.aiongraphos.domain.calculator


import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart
import org.aioncyclus.aiongraphos.domain.model.chart.ChartContext
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem.*
import org.aioncyclus.aiongraphos.domain.model.planet.Planet



class ChartCalculator(val planetCalculator: PlanetCalculator,
                      val housesCalculator: HousesCalculator) {

    fun calculate(planets:List<Planet>,
                  chartContext: ChartContext,
                  node : Planet,
                  houseSystem: HouseSystem=PLACIDUS
    ): AstroChart
    {
        val planetaryData=buildList {
            for(planet in planets)
            {
                add(planetCalculator.calculate(planet,chartContext.contextDate))
            }
        }
        val nodeData=planetCalculator.calculateNodeData(node, chartContext.contextDate)

        val housesData=housesCalculator.calculate(chartContext.latitude,chartContext.longitude,
            chartContext.contextDate,houseSystem)

        return AstroChart(planetaryData,nodeData,housesData,chartContext)
    }
}