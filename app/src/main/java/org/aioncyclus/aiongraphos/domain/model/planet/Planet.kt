package org.aioncyclus.aiongraphos.domain.model.planet

import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign
import swisseph.SweConst

// TODO:
// Planet currently contains Swiss Ephemeris identifiers.
// If alternative ephemeris backends are introduced,
// move seId into a SwissEphemerisMapper.

/**
 * Represents celestial bodies for use in astrological calculations
 *
 * @property seId Swiss Ephemeris constant identifier
 * @property symbol Unicode astrological symbol
 * @property planetName Planet's name
 * @property meanSpeed The mean speed of motion of a planet through the ecliptic
 * @see swisseph.SweConst
 */
enum class Planet(
    val seId: Int,
    val symbol: String,
    val planetName: String,
    val meanSpeed: Double
)
{

    MOON(
        SweConst.SE_MOON,
        "☽",
        "Moon",
        13.1764
    ),
    MERCURY(
        SweConst.SE_MERCURY,
        "☿",
        "Mercury",
        1.3834
    ),
    VENUS(
        SweConst.SE_VENUS,
        "♀",
        "Venus",
        1.2
    ),
    SUN(
        SweConst.SE_SUN,
        "☉",
        "Sun",
        0.9856
    ),
    MARS(
        SweConst.SE_MARS,
        "♂",
        "Mars",
        0.5578
    ),
    JUPITER(
        SweConst.SE_JUPITER,
        "♃",
        "Jupiter",
        0.0831
    ),
    SATURN(
        SweConst.SE_SATURN,
        "♄",
        "Saturn",
        0.0335
    ),
    URANUS(
        SweConst.SE_URANUS,
        "♅",
        "Uranus",
        0.0117
    ),
    NEPTUNE(
        SweConst.SE_NEPTUNE,
        "♆",
        "Neptune",
        0.0059
    ),
    PLUTO(
        SweConst.SE_PLUTO,
        "♇",
        "Pluto",
        0.0039
    ),
    MEAN_NORTH_NODE(
        SweConst.SE_MEAN_NODE,
        "☊",
        "Mean North Node",
        0.05295
    ),
    MEAN_SOUTH_NODE(
        SweConst.SE_MEAN_NODE,
        "☋",
        "Mean South Node",
        0.05295
    ),
    TRUE_NORTH_NODE(
        SweConst.SE_TRUE_NODE,
        "☊",
        "True North Node",
        0.05295
    ),
    TRUE_SOUTH_NODE(
        SweConst.SE_TRUE_NODE,
        "☋",
        "True South Node",
        0.05295
    )
}