package org.aioncyclus.aiongraphos.domain.model.zodiac.rulerships

import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign

interface RulershipSystem {

    val rulerships : Map<Planet, EssentialRulerships>

    fun of(planet: Planet): EssentialRulerships =
        rulerships[planet]
            ?: EssentialRulerships(
                domiciles = emptyList(),
                exaltation = null
            )

    fun domicileRulerOf(sign: Sign): Planet?
    {
        for(planet in Planet.entries)
        {
            val rulerships=of(planet)
            if(rulerships.domiciles.contains(sign))
            {
                return planet
            }
        }
        return null
    }
    fun exaltationRulerOf(sign: Sign): Planet?
    {
        for(planet in Planet.entries)
        {
            val rulerships=of(planet)
            if(rulerships.exaltation==sign)
            {
                return planet
            }
        }
        return null
    }
}