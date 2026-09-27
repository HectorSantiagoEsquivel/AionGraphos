package org.aioncyclus.aiongraphos.domain.model.zodiac.triplicity

import org.aioncyclus.aiongraphos.domain.model.chart.Sect
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.zodiac.Element

object DorotheanTriplicity: TriplicitySystem {

    override fun rulerOf(element: Element, sect: Sect): Planet {
        return when (element) {
            Element.FIRE -> when (sect) {
                Sect.DAY -> Planet.SUN
                Sect.NIGHT -> Planet.JUPITER
            }
            Element.EARTH -> when (sect) {
                Sect.DAY -> Planet.VENUS
                Sect.NIGHT -> Planet.MOON
            }
            Element.AIR -> when (sect) {
                Sect.DAY -> Planet.SATURN
                Sect.NIGHT -> Planet.MERCURY
            }
            Element.WATER -> Planet.MARS
        }
    }
}