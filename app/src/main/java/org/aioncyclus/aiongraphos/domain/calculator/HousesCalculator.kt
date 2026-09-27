package org.aioncyclus.aiongraphos.domain.calculator

import org.aioncyclus.aiongraphos.data.ephemeris.AstroEngine
import org.aioncyclus.aiongraphos.domain.model.house.House
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem.PLACIDUS
import org.aioncyclus.aiongraphos.domain.model.house.HousesData
import java.time.Instant



class HousesCalculator(
    private val engine: AstroEngine
) {
    fun calculate(latitude: Double,
                  longitude: Double,
                  contextDate: Instant= Instant.now(),
                  houseSystem: HouseSystem=PLACIDUS): HousesData
    {
        return engine.calculateHouses(latitude,longitude,contextDate,houseSystem)
    }

    companion object
    {
        fun locateHouseOf(
            longitude: Double,
            housesData: HousesData
        ): House {

            val houses = housesData.houses
            for (i in houses.indices) {
                val current = houses[i]
                val next = houses[(i + 1) % houses.size]
                if (current.cusp < next.cusp) {

                    if (longitude >= current.cusp &&
                        longitude < next.cusp) {
                        return current
                    }
                }
                else
                {
                    if (longitude >= current.cusp ||
                        longitude < next.cusp) {
                        return current
                    }
                }
            }

            throw IllegalStateException(
                "No house found for longitude $longitude"
            )
        }
    }
}