package org.aioncyclus.aiongraphos.domain.analyst

import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart
import org.aioncyclus.aiongraphos.domain.model.chart.ChartAnalysis
import org.aioncyclus.aiongraphos.domain.model.chart.Sect
import org.aioncyclus.aiongraphos.domain.model.dignity.DignitySystem
import org.aioncyclus.aiongraphos.domain.model.lot.LotType
import org.aioncyclus.aiongraphos.domain.model.planet.Planet

class ChartAnalyser(
    val aspectCalculator: AspectCalculator = AspectCalculator(),
    val lotCalculator: LotCalculator = LotCalculator(),
    val sectCalculator: SectCalculator= SectCalculator(),

)
{
    fun analyse(astroChart: AstroChart, lotsToCalculate: List<LotType>,dignitySystem: DignitySystem): ChartAnalysis
    {
        val aspects= aspectCalculator.calculateAspects(astroChart.planetaryData)
        val sect= sectCalculator.determine(astroChart)
        val lotsData= lotCalculator.calculateLots(astroChart,sect,lotsToCalculate);

        return ChartAnalysis(aspects,lotsData,sect)
    }


}