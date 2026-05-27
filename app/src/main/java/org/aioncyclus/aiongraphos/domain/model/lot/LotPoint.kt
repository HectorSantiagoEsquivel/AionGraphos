package org.aioncyclus.aiongraphos.domain.model.lot

import org.aioncyclus.aiongraphos.domain.model.planet.Planet

sealed class LotPoint {
    object Ascendant : LotPoint()
    object Midheaven : LotPoint()
    data class PlanetPoint(val planet: Planet) : LotPoint()
    data class BaseLot(val lotType: LotType) : LotPoint()
}