package org.aioncyclus.aiongraphos.ui.screens.planetdetail


import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.aspect.Distance
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.zodiac.ZodiacPosition
import org.aioncyclus.aiongraphos.ui.components.dignitychart.PlanetDignitySample

data class PlanetDetailUIState(
    val planet: Planet= Planet.SUN,
    val isRetrograde: Boolean = true,
    val zodiacPosition: ZodiacPosition?=null,
    val planetSpeed: Distance = Distance(0, 0, 0.0),
    val speedGaugeValue: Double = 0.0,
    val score:Int=0,
    val strength: Double =0.0,
    val aspects: List<Aspect> = emptyList<Aspect>(),
    val dignityTimeline:List<PlanetDignitySample> = emptyList<PlanetDignitySample>()
)