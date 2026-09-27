package org.aioncyclus.aiongraphos.domain.analyst

import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData

object LuminaryCalculator {

    fun isWaxing(
        moonData: PlanetData,
        sunData: PlanetData
    ): Boolean {

        val moonLongitude = moonData.planetPosition.longitude
        val sunLongitude = sunData.planetPosition.longitude

        val distance =
            (moonLongitude - sunLongitude + 360.0) % 360.0

        return distance < 180.0
    }

    fun isWaning(
        moonData: PlanetData,
        sunData: PlanetData
    ): Boolean {
        return !isWaxing(moonData, sunData)
    }
}