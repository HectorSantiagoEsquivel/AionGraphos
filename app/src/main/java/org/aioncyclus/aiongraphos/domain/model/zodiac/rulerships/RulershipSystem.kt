package org.aioncyclus.aiongraphos.domain.model.zodiac.rulerships

import org.aioncyclus.aiongraphos.domain.model.planet.Planet

interface RulershipSystem {

    val rulerships : Map<Planet, EssentialRulerships>

    fun of(planet: Planet): EssentialRulerships =
        rulerships[planet]
            ?: EssentialRulerships(
                domiciles = emptyList(),
                exaltation = null
            )

}