package org.aioncyclus.aiongraphos.domain.analyst

import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart
import org.aioncyclus.aiongraphos.domain.model.chart.Sect
import org.aioncyclus.aiongraphos.domain.model.dignity.DignitySystem
import org.aioncyclus.aiongraphos.domain.model.planet.Condition

//TODO:
// - This class could be an object
class ConditionCalculator {

    fun calculateChartConditions(astroChart: AstroChart,
                                 sect: Sect,
                                 aspects: List<Aspect>,
                                 dignitySystem: DignitySystem)
    :List<Condition>
    {
        val conditions=buildList {
            for(planetData in astroChart.planetaryData)
            {
                add(dignitySystem.evaluateCondition(planetData.planet,astroChart,sect,aspects))
            }
        }
        return conditions
    }


}