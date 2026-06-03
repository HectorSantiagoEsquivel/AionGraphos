package org.aioncyclus.aiongraphos.domain.model.dignity

import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign

interface DignitySystem {

    fun essentialRulershipsOf(
        planet: Planet
    ): EssentialRulerships

    fun isInDomicile(planetData: PlanetData): Boolean
    {
        return essentialRulershipsOf(planetData.planet).domiciles.contains(planetData.zodiacPosition.sign)
    }

    fun isInExaltation(planetData: PlanetData): Boolean
    {
        return essentialRulershipsOf(planetData.planet).exaltation==planetData.zodiacPosition.sign
    }

    fun isInDecan(planetData: PlanetData): Boolean
    {
        return planetData.zodiacPosition.decan.ruler==planetData.planet
    }

    fun isInExile(planetData: PlanetData): Boolean
    {
        return exileOf(planetData.planet).contains(planetData.zodiacPosition.sign)
    }

    fun isInFall(planetData: PlanetData): Boolean
    {
        return fallOf(planetData.planet)==planetData.zodiacPosition.sign
    }


    fun fallOf(planet: Planet): Sign?= essentialRulershipsOf(planet).exaltation?.opposite()
    fun exileOf(planet: Planet): List<Sign> = essentialRulershipsOf(planet).domiciles.map { it.opposite() }
}
