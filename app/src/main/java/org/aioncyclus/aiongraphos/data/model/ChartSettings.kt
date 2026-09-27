package org.aioncyclus.aiongraphos.data.model

import kotlinx.serialization.Serializable
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem
import org.aioncyclus.aiongraphos.domain.model.lot.LotType
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.dignity.DignitySystemType


@Serializable
data class ChartSettings(
    val planets:List<Planet>,
    val lots: List<LotType>,
    val houseSystem: HouseSystem,
    val isTrueNode: Boolean,
    val dignitySystemType: DignitySystemType
)



