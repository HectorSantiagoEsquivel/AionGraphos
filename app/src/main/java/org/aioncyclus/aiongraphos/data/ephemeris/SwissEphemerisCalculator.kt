package org.aioncyclus.aiongraphos.data.ephemeris
import org.aioncyclus.aiongraphos.domain.calculator.ZodiacMapper
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem
import org.aioncyclus.aiongraphos.domain.model.house.HousesData
import org.aioncyclus.aiongraphos.domain.model.house.House
import org.aioncyclus.aiongraphos.domain.model.house.Angles


import swisseph.*
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetPosition
import java.time.Instant
import java.time.ZoneOffset


class SwissEphemerisCalculator(private val swe: SwissEph): AstroEngine
{
    /**
     * Calculates the celestial position of a planet at a given moment.
     *
     * This method queries the Swiss Ephemeris for the planet's geocentric position,
     * returning both angular coordinates (longitude, latitude) and distance,
     * along with their velocities.
     *
     * @param planet The planet to calculate position for. Defaults to [Planet.MERCURY]
     * @param contextDate The moment in time for the calculation. Defaults to current time
     * @return A [PlanetPosition] containing the planet's coordinates and velocities
     *
     * @throws IllegalStateException If the Swiss Ephemeris calculation fails
     *
     * @see PlanetPosition
     * @see swe_calc_ut
     */
    override fun calculatePlanetPosition(planet: Planet, contextDate: Instant): PlanetPosition {
        return try {
            val julDay = calculateJulianDay(contextDate)
            val planetPos = DoubleArray(6)
            val serr = StringBuffer()
            val flags = SweConst.SEFLG_SWIEPH or SweConst.SEFLG_SPEED

            val result = swe.swe_calc_ut(julDay, planet.seId, flags, planetPos, serr)

            if (result == SweConst.ERR) {
                throw IllegalStateException("Failed to calculate position: $serr")
            }


            PlanetPosition(planetPos[0], planetPos[1], planetPos[2],
                planetPos[3], planetPos[4], planetPos[5])
        } catch (e: Exception) {

            println("Error calculating planet position: ${e.message}")

            throw IllegalStateException("Failed to calculate position for ${planet.planetName}", e)
        }
    }

    override fun calculateHouses(
        latitude: Double,
        longitude: Double,
        contextDate: Instant,
        houseSystem: HouseSystem
    ): HousesData {
        return try {
            val julDay = calculateJulianDay(contextDate)
            val cusps = DoubleArray(13)
            val ascmc= DoubleArray(10)


            val result = swe.swe_houses(julDay,0,latitude,longitude,houseSystem.seId,cusps,ascmc)

            if (result == SweConst.ERR) {
                throw IllegalStateException("Failed to calculate houses")
            }

            val houses=buildList {
                for(i in 1..12)
                {
                    val zodiacPosition= ZodiacMapper.fromLongitude(cusps[i])
                    add(House(i,cusps[i],zodiacPosition))
                }
            }
            val angles=Angles(ascmc[0],ascmc[1])
            HousesData(houses,angles)


        } catch (e: Exception)
        {

        println("Error calculating planet position: ${e.message}")

        throw IllegalStateException("Failed to calculate house positions", e)
        }
    }

    /**
     * Converts an [Instant] to a Julian Day Number for Swiss Ephemeris calculations.
     *
     * Julian Day is the standard time format used by astronomical software,
     * representing the number of days since January 1, 4713 BCE.
     *
     * @param contextDate The moment in time to convert
     * @return The corresponding Julian Day number as a Double
     *
     * @see SweDate.getJulDay
     */
    fun calculateJulianDay(contextDate:Instant): Double
    {
        val utcDate=contextDate.atZone(ZoneOffset.UTC)

        val year=utcDate.year
        val month=utcDate.monthValue
        val day=utcDate.dayOfMonth
        val hour =utcDate.hour
        val minute=utcDate.minute
        val second=utcDate.second
        val nano=utcDate.nano
        val decimalHour=calculateDecimalHour(hour,minute,second,nano)

        return SweDate.getJulDay(year,month,day,decimalHour)
    }

    /**
     * Converts hours, minutes, and seconds to a decimal hour value.
     *
     * @param hour The hour component (0-23)
     * @param minute The minute component (0-59)
     * @param second The second component (0-59). Defaults to 0
     * @param nano The nanosecond component (0-59). Defaults to 0
     * @return The time as a decimal hour (e.g., 14:30:00 = 14.5)
     */
    fun calculateDecimalHour(
        hour: Int,
        minute: Int,
        second: Int = 0,
        nano: Int = 0
    ): Double {
        return hour +
                (minute / 60.0) +
                (second / 3600.0) +
                (nano / 1000000000.0 / 3600.0)
    }
}