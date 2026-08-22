package org.aioncyclus.aiongraphos.data.repository


import org.aioncyclus.aiongraphos.data.location.LocationTracker
import org.aioncyclus.aiongraphos.data.location.TimeZoneResolver
import org.aioncyclus.aiongraphos.domain.model.location.Location
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocationRepository @Inject constructor(
    private val locationTracker: LocationTracker
)
{
    private var currentLocation: Location?=null

    suspend fun useCurrentLocation(): Location? {
        val androidLocation = locationTracker.getCurrentLocation()
            ?: return null
        val longitude=androidLocation.longitude
        val latitude=androidLocation.latitude

        val zoneId=TimeZoneResolver.resolve(latitude,longitude)
        val location= Location(
            name = "Current",
            zoneID = ZoneId.of(zoneId),
            latitude = latitude,
            longitude =longitude
        )
        currentLocation=location
        return location
    }

    fun useSavedLocation(location: Location) {
        currentLocation = location
    }

    fun getCurrentLocation(): Location? = currentLocation
}