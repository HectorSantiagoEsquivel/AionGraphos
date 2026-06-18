package org.aioncyclus.aiongraphos.domain.model.zodiac.term

import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign

object PtolemaicTerms: TermSystem
{
    val terms: Map<Sign, List<Term>> = mapOf(
        Sign.ARIES to listOf(
            Term(0, 6, Planet.JUPITER),
            Term(7, 14, Planet.VENUS),
            Term(15, 21, Planet.MERCURY),
            Term(22, 26, Planet.MARS),
            Term(27, 29, Planet.SATURN)
        ),
        Sign.TAURUS to listOf(
            Term(0, 8, Planet.VENUS),
            Term(9, 15, Planet.MERCURY),
            Term(16, 22, Planet.JUPITER),
            Term(23, 27, Planet.SATURN),
            Term(28, 29, Planet.MARS)
        ),
        Sign.GEMINI to listOf(
            Term(0, 7, Planet.MERCURY),
            Term(8, 14, Planet.JUPITER),
            Term(15, 21, Planet.VENUS),
            Term(22, 25, Planet.SATURN),
            Term(26, 29, Planet.MARS)
        ),
        Sign.CANCER to listOf(
            Term(0, 6, Planet.MARS),
            Term(7, 13, Planet.JUPITER),
            Term(14, 20, Planet.MERCURY),
            Term(21, 27, Planet.VENUS),
            Term(28, 29, Planet.SATURN)
        ),
        Sign.LEO to listOf(
            Term(0, 6, Planet.SATURN),
            Term(7, 13, Planet.MERCURY),
            Term(14, 18, Planet.VENUS),
            Term(19, 25, Planet.JUPITER),
            Term(26, 29, Planet.MARS)
        ),
        Sign.VIRGO to listOf(
            Term(0, 7, Planet.MERCURY),
            Term(8, 13, Planet.VENUS),
            Term(14, 18, Planet.JUPITER),
            Term(19, 24, Planet.SATURN),
            Term(25, 29, Planet.MARS)
        ),
        Sign.LIBRA to listOf(
            Term(0, 6, Planet.SATURN),
            Term(7, 11, Planet.VENUS),
            Term(12, 19, Planet.JUPITER),
            Term(20, 24, Planet.MERCURY),
            Term(25, 29, Planet.MARS)
        ),
        Sign.SCORPIO to listOf(
            Term(0, 6, Planet.MARS),
            Term(7, 14, Planet.JUPITER),
            Term(15, 21, Planet.VENUS),
            Term(22, 27, Planet.MERCURY),
            Term(28, 29, Planet.SATURN)
        ),
        Sign.SAGITTARIUS to listOf(
            Term(0, 8, Planet.JUPITER),
            Term(9, 14, Planet.VENUS),
            Term(15, 19, Planet.MERCURY),
            Term(20, 25, Planet.SATURN),
            Term(26, 29, Planet.MARS)
        ),
        Sign.CAPRICORN to listOf(
            Term(0, 6, Planet.VENUS),
            Term(7, 12, Planet.MERCURY),
            Term(13, 19, Planet.JUPITER),
            Term(20, 25, Planet.MARS),
            Term(26, 29, Planet.SATURN)
        ),
        Sign.AQUARIUS to listOf(
            Term(0, 6, Planet.SATURN),
            Term(7, 12, Planet.MERCURY),
            Term(13, 20, Planet.VENUS),
            Term(21, 25, Planet.JUPITER),
            Term(26, 29, Planet.MARS)
        ),
        Sign.PISCES to listOf(
            Term(0, 8, Planet.VENUS),
            Term(9, 14, Planet.JUPITER),
            Term(15, 20, Planet.MERCURY),
            Term(21, 26, Planet.MARS),
            Term(27, 29, Planet.SATURN)
        )
    )
    override fun rulerOf(
        sign: Sign,
        degree: Int
    ): Planet? {

        return terms[sign]
            ?.find {
                degree >= it.startDegree
                        &&
                degree <= it.endDegree
            }
            ?.ruler
    }
}