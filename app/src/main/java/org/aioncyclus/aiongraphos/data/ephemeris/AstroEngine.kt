package org.aioncyclus.aiongraphos.data.ephemeris
import org.aioncyclus.aiongraphos.domain.model.house.HousesData
import org.aioncyclus.aiongraphos.domain.model.planet.Planet.*
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetPosition
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem.*
import java.time.Instant


/**
 * Contract for astronomical calculation engines.
 *
 * Provides geocentric planet positions for astrological calculations.
 *
 * @see PlanetPosition
 * @see SwissEphemerisCalculator
 */
interface AstroEngine {
    /**
     * Calculates a planet's geocentric position at a given time.
     *
     * @param planet The planet to calculate (default: Mercury)
     * @param contextDate The calculation time (default: now)
     * @return The planet's position including coordinates and velocities
     */
    fun calculatePlanetPosition(planet: Planet = MERCURY,
                                contextDate: Instant = Instant.now()
    ): PlanetPosition

    fun calculateHouses(latitude: Double,
                        longitude: Double,
                        contextDate: Instant = Instant.now(),
                        houseSystem: HouseSystem= WHOLE
    ): HousesData
}