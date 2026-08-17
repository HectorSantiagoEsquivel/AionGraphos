package org.aioncyclus.aiongraphos.data.location

import us.dustinj.timezonemap.TimeZoneMap

/*object TimeZoneResolver {
    private val delta=1.0

    private var map: TimeZoneMap? = null

    private var minLongitude = 0.0
    private var minLatitude = 0.0
    private var maxLongitude = 0.0
    private var maxLatitude = 0.0

    fun resolve(latitude: Double, longitude: Double): String? {

        if (
            map == null ||
            longitude < minLongitude ||
            longitude > maxLongitude ||
            latitude < minLatitude ||
            latitude > maxLatitude
        ) {
            minLongitude = longitude - delta
            minLatitude = latitude - delta
            maxLongitude = longitude + delta
            maxLatitude = latitude + delta

            map = TimeZoneMap.forRegion(
                minLatitude,
                minLongitude,
                maxLatitude,
                maxLongitude
            )
        }

        return map
            ?.getOverlappingTimeZone(latitude, longitude)
            ?.zoneId
    }
}*/