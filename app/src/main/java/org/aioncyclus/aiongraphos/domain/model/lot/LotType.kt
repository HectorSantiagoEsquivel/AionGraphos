package org.aioncyclus.aiongraphos.domain.model.lot

import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import kotlinx.serialization.Serializable

@Serializable(with = LotTypeSerializer::class)
sealed class LotType(
    val id: String,
    val name: String,
    val diurnalFormula: LotFormula,
    val nocturnalFormula: LotFormula
) {
    object Fortune : LotType(
        id = "fortune",
        name = "Fortune",
        diurnalFormula = LotFormula(
            LotPoint.Ascendant,
            LotPoint.PlanetPoint(Planet.MOON),
            LotPoint.PlanetPoint(Planet.SUN)
        ),
        nocturnalFormula = LotFormula(
            LotPoint.Ascendant,
            LotPoint.PlanetPoint(Planet.SUN),
            LotPoint.PlanetPoint(Planet.MOON)
        )
    )

    object Spirit : LotType(
        id = "spirit",
        name = "Spirit",
        diurnalFormula = LotFormula(
            LotPoint.Ascendant,
            LotPoint.PlanetPoint(Planet.SUN),
            LotPoint.PlanetPoint(Planet.MOON)
        ),
        nocturnalFormula = LotFormula(
            LotPoint.Ascendant,
            LotPoint.PlanetPoint(Planet.MOON),
            LotPoint.PlanetPoint(Planet.SUN)
        )
    )

    object Eros : LotType(
        id = "eros",
        name = "Eros",
        diurnalFormula = LotFormula(
            LotPoint.Ascendant,
            LotPoint.PlanetPoint(Planet.VENUS),
            LotPoint.BaseLot(Spirit)
        ),
        nocturnalFormula = LotFormula(
            LotPoint.Ascendant,
            LotPoint.BaseLot(Spirit),
            LotPoint.PlanetPoint(Planet.VENUS)
        )
    )

    object Exaltation : LotType(
        id = "exaltation",
        name = "Exaltation",
        diurnalFormula = LotFormula(
            LotPoint.Ascendant,
            LotPoint.BaseLot(Fortune),
            LotPoint.PlanetPoint(Planet.SUN)
        ),
        nocturnalFormula = LotFormula(
            LotPoint.Ascendant,
            LotPoint.BaseLot(Fortune),
            LotPoint.PlanetPoint(Planet.MOON)
        )
    )

    object Basis : LotType(
        id = "basis",
        name = "Basis",
        diurnalFormula = LotFormula(
            LotPoint.Ascendant,
            LotPoint.BaseLot(Fortune),
            LotPoint.PlanetPoint(Planet.SATURN)
        ),
        nocturnalFormula = LotFormula(
            LotPoint.Ascendant,
            LotPoint.BaseLot(Fortune),
            LotPoint.PlanetPoint(Planet.SATURN)
        )
    )
    /*companion object {
        val all = listOf(
            Fortune,
            Spirit,
            Eros,
            Exaltation,
            Basis
        )
    }*/
}
val allLotTypes = listOf(
    LotType.Fortune,
    LotType.Spirit,
    LotType.Eros,
    LotType.Exaltation,
    LotType.Basis
)

data class LotFormula(
    val point1: LotPoint,
    val point2: LotPoint,
    val point3: LotPoint
)
