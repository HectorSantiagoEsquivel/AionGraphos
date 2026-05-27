package org.aioncyclus.aiongraphos.domain.model.chart

import java.time.Instant
import java.time.ZoneId

/**
 * Represents an exact moment of time, alongside geolocation, to facilitate astrological chart calculations.
 *
 * This class combines temporal and spatial data needed to calculate a celestial chart.
 * The combination of date, time, and location determines the planetary positions and house cusps for a given moment.
 *
 * @property contextDate The exact moment of the analyzed chart
 * @property zoneID The timezone identifier for the queried location
 * @property latitude The geographic latitude in degrees (optional, for location-based charts)
 * @property longitude The geographic longitude in degrees (optional, for location-based charts)
 *
 * @see java.time.Instant
 * @see java.time.ZoneId
 */
class ChartContext(
    val contextDate: Instant,
    val zoneID: ZoneId,
    val latitude: Double,
    val longitude: Double
)