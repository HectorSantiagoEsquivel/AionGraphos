package org.aioncyclus.aiongraphos.domain.model.planet

import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign
import swisseph.SweConst

/**
 * Represents celestial bodies for use in astrological calculations
 *
 * @property seId Swiss Ephemeris constant identifier
 * @property symbol Unicode astrological symbol
 * @property planetName Planet's name
 *
 * @see swisseph.SweConst
 */
enum class Planet(
    val seId: Int,
    val symbol: String,
    val planetName: String,
)
{
    SUN(
        SweConst.SE_SUN,
        "☉",
        "Sun"
    ),
    MOON(
        SweConst.SE_MOON,
        "☽",
        "Moon"
    ),
    MERCURY(
        SweConst.SE_MERCURY,
        "☿",
        "Mercury"
    ),
    VENUS(
        SweConst.SE_VENUS,
        "♀",
        "Venus"
    ),
    MARS(
        SweConst.SE_MARS,
        "♂",
        "Mars"
    ),
    JUPITER(
        SweConst.SE_JUPITER,
        "♃",
        "Jupiter"
    ),
    SATURN(
        SweConst.SE_SATURN,
        "♄",
        "Saturn"
    ),
    URANUS(
        SweConst.SE_URANUS,
        "♅",
        "Uranus"
    ),
    NEPTUNE(
        SweConst.SE_NEPTUNE,
        "♆",
        "Neptune"
    ),
    PLUTO(
        SweConst.SE_PLUTO,
        "♇",
        "Pluto"
    )


}