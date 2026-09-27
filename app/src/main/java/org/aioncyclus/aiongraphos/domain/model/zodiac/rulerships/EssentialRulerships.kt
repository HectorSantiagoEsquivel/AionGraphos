package org.aioncyclus.aiongraphos.domain.model.zodiac.rulerships

import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign

data class EssentialRulerships(
    val domiciles: List<Sign>,
    val exaltation: Sign?
)
