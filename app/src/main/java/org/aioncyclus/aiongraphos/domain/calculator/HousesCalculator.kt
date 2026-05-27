package org.aioncyclus.aiongraphos.domain.calculator

import org.aioncyclus.aiongraphos.data.ephemeris.AstroEngine
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
}