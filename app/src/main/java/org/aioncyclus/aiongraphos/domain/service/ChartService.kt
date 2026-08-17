package org.aioncyclus.aiongraphos.domain.service

import org.aioncyclus.aiongraphos.data.ephemeris.AstroEngine
import org.aioncyclus.aiongraphos.domain.analyst.AspectCalculator
import org.aioncyclus.aiongraphos.domain.analyst.ChartAnalyser
import org.aioncyclus.aiongraphos.domain.analyst.LotCalculator
import org.aioncyclus.aiongraphos.domain.calculator.ChartCalculator
import org.aioncyclus.aiongraphos.domain.calculator.HousesCalculator
import org.aioncyclus.aiongraphos.domain.calculator.PlanetCalculator
import org.aioncyclus.aiongraphos.domain.model.chart.AnalysedChart
import org.aioncyclus.aiongraphos.domain.model.chart.ChartContext
import org.aioncyclus.aiongraphos.domain.model.dignity.DignitySystem
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem.PLACIDUS
import org.aioncyclus.aiongraphos.domain.model.lot.LotType
import org.aioncyclus.aiongraphos.domain.model.planet.Planet

class ChartService(
    private val chartCalculator: ChartCalculator,
    private val chartAnalyser: ChartAnalyser
){


    fun calculateAnalysedChart(planets:List<Planet>,
                               lots:List<LotType>,
                               chartContext: ChartContext,
                               dignitySystem: DignitySystem,
                               node:Planet= Planet.MEAN_NORTH_NODE,
                               houseSystem: HouseSystem=PLACIDUS
    ): AnalysedChart
    {
        val astroChart= chartCalculator.calculate(planets,chartContext,node,houseSystem)
        val chartAnalysis= chartAnalyser.analyse(astroChart,lots,dignitySystem)
        return AnalysedChart(astroChart,chartAnalysis)
    }

}