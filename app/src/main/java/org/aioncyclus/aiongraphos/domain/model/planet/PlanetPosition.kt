package org.aioncyclus.aiongraphos.domain.model.planet

/**
 * Represents a singular planet's position in a given moment of time.
 *
 * @property longitude The Planet's ecliptic longitude in degrees
 * @property latitude The Planet's ecliptic latitude in degrees
 * @property distanceAU The Planet's distance in Astronomical Units (AU)
 * @property speedLongitude The Planet's speed in longitude (degree / day)
 * @property speedLatitude The Planet's speed in latitude (degree / day)
 * @property speedDistance The Planet's speed in distance (AU / day)
 *
 * @see org.aioncyclus.aiongraphos.domain.model.planet
 */
data class PlanetPosition(
    val longitude: Double,
    val latitude: Double,
    val distanceAU: Double,
    val speedLongitude: Double,
    val speedLatitude: Double,
    val speedDistance: Double
)