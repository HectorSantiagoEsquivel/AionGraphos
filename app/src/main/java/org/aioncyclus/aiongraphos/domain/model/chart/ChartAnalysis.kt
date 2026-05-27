package org.aioncyclus.aiongraphos.domain.model.chart

import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.lot.LotData

data class ChartAnalysis(
    val aspects: List<Aspect>,
    val lots: List<LotData>,
)