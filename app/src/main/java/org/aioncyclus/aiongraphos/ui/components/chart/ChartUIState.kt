package org.aioncyclus.aiongraphos.ui.components.chart

import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.house.HousesData
import org.aioncyclus.aiongraphos.domain.model.lot.LotData
import org.aioncyclus.aiongraphos.domain.model.lot.LotType
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData


data class ChartUIState(
    val planetaryData: List<PlanetData>,
    val housesData: HousesData,
    val lotData: List<LotData>,
    val aspects:List<Aspect>,
    val nodeData: List<PlanetData>
)