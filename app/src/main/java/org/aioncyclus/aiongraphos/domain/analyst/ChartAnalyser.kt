package org.aioncyclus.aiongraphos.domain.analyst

import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart
import org.aioncyclus.aiongraphos.domain.model.chart.ChartAnalysis
import org.aioncyclus.aiongraphos.domain.model.lot.LotType

class ChartAnalyser(
    val aspectCalculator: AspectCalculator = AspectCalculator(),
    val lotCalculator: LotCalculator = LotCalculator()
)
{
    fun analyse(astroChart: AstroChart, lotsToCalculate: List<LotType>): ChartAnalysis
    {
        val aspects= aspectCalculator.calculateAspects(astroChart.planetaryData)
        val lotsData= lotCalculator.calculateLots(astroChart,lotsToCalculate);

        return ChartAnalysis(aspects,lotsData)
    }
}