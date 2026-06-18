package org.aioncyclus.aiongraphos.domain.analyst

import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart
import org.aioncyclus.aiongraphos.domain.model.chart.ChartAnalysis
import org.aioncyclus.aiongraphos.domain.model.dignity.DignitySystem
import org.aioncyclus.aiongraphos.domain.model.lot.LotType


//TODO:
// - This class could be an object
class ChartAnalyser(
    val aspectCalculator: AspectCalculator = AspectCalculator(),
    val lotCalculator: LotCalculator = LotCalculator(),
    val sectCalculator: SectCalculator= SectCalculator(),
    val dignityCalculator: ConditionCalculator =ConditionCalculator()

)
{
    fun analyse(astroChart: AstroChart, lotsToCalculate: List<LotType>,dignitySystem: DignitySystem): ChartAnalysis
    {
        val aspects= aspectCalculator.calculateAspects(astroChart.planetaryData+astroChart.nodeData)
        val sect= sectCalculator.determine(astroChart)
        val lotsData= lotCalculator.calculateLots(astroChart,sect,lotsToCalculate);
        val conditions= dignityCalculator.calculateChartConditions(astroChart,sect,aspects,dignitySystem)
        return ChartAnalysis(aspects,lotsData,sect,conditions)
    }


}