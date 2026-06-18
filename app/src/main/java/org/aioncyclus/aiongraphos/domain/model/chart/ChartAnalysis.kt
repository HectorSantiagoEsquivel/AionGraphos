package org.aioncyclus.aiongraphos.domain.model.chart

import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.lot.LotData
import org.aioncyclus.aiongraphos.domain.model.planet.Condition

data class ChartAnalysis(
    val aspects: List<Aspect>,
    val lots: List<LotData>,
    val sect: Sect,
    val conditions: List<Condition>
)