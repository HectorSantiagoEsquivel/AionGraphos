package org.aioncyclus.aiongraphos.domain.model.dignity

import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.chart.AnalysedChart
import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart
import org.aioncyclus.aiongraphos.domain.model.chart.Sect
import org.aioncyclus.aiongraphos.domain.model.planet.Condition
import org.aioncyclus.aiongraphos.domain.model.planet.Planet


interface DignitySystem {

    fun evaluateCondition(
        planet: Planet,
        astroChart: AstroChart,
        sect: Sect,
        apects: List<Aspect>,
    ): Condition
}
