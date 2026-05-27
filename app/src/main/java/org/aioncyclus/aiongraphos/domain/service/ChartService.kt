package org.aioncyclus.aiongraphos.domain.service

import android.content.Context
import org.aioncyclus.aiongraphos.data.ephemeris.SwissEphemerisCalculator
import org.aioncyclus.aiongraphos.data.ephemeris.SwissEphemerisProvider
import org.aioncyclus.aiongraphos.domain.analyst.AspectCalculator
import org.aioncyclus.aiongraphos.domain.analyst.ChartAnalyser
import org.aioncyclus.aiongraphos.domain.analyst.LotCalculator
import org.aioncyclus.aiongraphos.domain.calculator.ChartCalculator
import org.aioncyclus.aiongraphos.domain.calculator.HousesCalculator
import org.aioncyclus.aiongraphos.domain.calculator.PlanetCalculator
import org.aioncyclus.aiongraphos.domain.model.chart.AnalysedChart
import org.aioncyclus.aiongraphos.domain.model.chart.ChartContext
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem.PLACIDUS
import org.aioncyclus.aiongraphos.domain.model.lot.LotType
import org.aioncyclus.aiongraphos.domain.model.planet.Planet

class ChartService(
    context: Context
){
    private val swe = SwissEphemerisProvider.loadSwissEphemeris(context)
    private val astroEngine = SwissEphemerisCalculator(swe)
    private val planetCalculator= PlanetCalculator(astroEngine)
    private val housesCalculator= HousesCalculator(astroEngine)
    private val chartCalculator= ChartCalculator(planetCalculator,housesCalculator)
    private val aspectCalculator= AspectCalculator()
    private val lotCalculator= LotCalculator()
    private val chartAnalyser= ChartAnalyser(aspectCalculator,lotCalculator)

    fun calculateAnalysedChart(planets:List<Planet>,
                               lots:List<LotType>,
                               chartContext: ChartContext,
                               houseSystem: HouseSystem=PLACIDUS
    ): AnalysedChart
    {
        val astroChart= chartCalculator.calculate(planets,chartContext,houseSystem)
        val chartAnalysis= chartAnalyser.analyse(astroChart,lots)
        return AnalysedChart(astroChart,chartAnalysis)
    }

}