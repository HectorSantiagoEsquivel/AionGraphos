package org.aioncyclus.aiongraphos.domain.model.zodiac.triplicity

import org.aioncyclus.aiongraphos.domain.model.chart.Sect
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.zodiac.Element

interface TriplicitySystem {

    fun rulerOf(
        element: Element,
        sect: Sect
    ): Planet

}