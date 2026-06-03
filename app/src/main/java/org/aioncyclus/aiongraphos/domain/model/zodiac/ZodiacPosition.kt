package org.aioncyclus.aiongraphos.domain.model.zodiac

/**
 * Represents a singular zodiacal position in a given moment of time.
 *
 * @property sign The zodiacal sign on which the point is
 * @property degreeInSign The degree on the zodiacal sign on which the point is
 * @property minuteInDegree The minute within the current zodiacal degree
 * @property decan The decan occupied by the point
 * @see org.aioncyclus.aiongraphos.domain.model.planet.Planet
 */
data class ZodiacPosition(
    val sign: Sign,
    val degreeInSign: Int,
    val minuteInDegree: Int,
    val decan: Decan
)