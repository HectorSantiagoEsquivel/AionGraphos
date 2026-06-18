package org.aioncyclus.aiongraphos.domain.model.zodiac.term

import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign

data class Term(
    val startDegree: Int,
    val endDegree: Int,
    val ruler: Planet
)