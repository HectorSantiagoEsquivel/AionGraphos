package org.aioncyclus.aiongraphos.domain.model.dignity

import org.aioncyclus.aiongraphos.domain.model.chart.Sect
import org.aioncyclus.aiongraphos.domain.model.planet.Condition
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData
import org.aioncyclus.aiongraphos.domain.model.zodiac.Element
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign

class TraditionalDignities: DignitySystem {

    override fun essentialRulershipsOf(
        planet: Planet
    ): EssentialRulerships {
        return when (planet) {
            Planet.SUN ->
                EssentialRulerships(
                    domiciles = listOf(Sign.LEO),
                    exaltation = Sign.ARIES
                )
            Planet.MOON ->
                EssentialRulerships(
                    domiciles = listOf(Sign.CANCER),
                    exaltation = Sign.TAURUS
                )
            Planet.MERCURY ->
                EssentialRulerships(
                    domiciles = listOf(
                        Sign.GEMINI,
                        Sign.VIRGO
                    ),
                    exaltation = Sign.VIRGO
                )
            Planet.VENUS ->
                EssentialRulerships(
                    domiciles = listOf(
                        Sign.TAURUS,
                        Sign.LIBRA
                    ),
                    exaltation = Sign.PISCES
                )
            Planet.MARS ->
                EssentialRulerships(
                    domiciles = listOf(
                        Sign.ARIES,
                        Sign.SCORPIO
                    ),
                    exaltation = Sign.CAPRICORN
                )
            Planet.JUPITER ->
                EssentialRulerships(
                    domiciles = listOf(
                        Sign.SAGITTARIUS,
                        Sign.PISCES
                    ),
                    exaltation = Sign.CANCER
                )
            Planet.SATURN ->
                EssentialRulerships(
                    domiciles = listOf(
                        Sign.CAPRICORN,
                        Sign.AQUARIUS
                    ),
                    exaltation = Sign.LIBRA
                )
            Planet.URANUS ->
                EssentialRulerships(
                    domiciles = emptyList(),
                    exaltation = null
                )
            Planet.NEPTUNE ->
                EssentialRulerships(
                    domiciles = emptyList(),
                    exaltation = null
                )
            Planet.PLUTO ->
                EssentialRulerships(
                    domiciles = emptyList(),
                    exaltation = null
                )
        }
    }

    fun triplicityRulerOf(element: Element, sect: Sect): Planet {
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

    private fun isInTriplicity(planetData: PlanetData, sect: Sect): Boolean
    {
        val signElement =planetData.zodiacPosition.sign.element
        return planetData.planet == triplicityRulerOf(signElement,sect)
    }

    fun EssentialDignity(planetData: PlanetData, sect: Sect): Condition
    {
        var conditionScore = 0
        var hasEssentialDignity = false

        if(isInDomicile(planetData))
        {
            conditionScore += 5
            hasEssentialDignity = true
        }
        if(isInExaltation(planetData))
        {
            conditionScore += 4
            hasEssentialDignity = true
        }
        if(isInTriplicity(planetData,sect))
        {
            conditionScore += 3
            hasEssentialDignity = true
        }
        if(isInDecan(planetData))
        {
            conditionScore += 1
            hasEssentialDignity = true
        }
        if(isInExile(planetData))
        {
            conditionScore += -5
        }
        if(isInFall(planetData))
        {
            conditionScore += -4
        }
        if(hasEssentialDignity != true)
        {
            conditionScore += -5
        }

        return Condition(planetData.planet,conditionScore)
    }
}