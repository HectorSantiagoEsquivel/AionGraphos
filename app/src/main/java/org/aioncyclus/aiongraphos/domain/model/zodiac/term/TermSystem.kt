package org.aioncyclus.aiongraphos.domain.model.zodiac.term

import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign


 interface TermSystem {
        fun rulerOf(
            sign: Sign,
            degree: Int
        ): Planet?
}
