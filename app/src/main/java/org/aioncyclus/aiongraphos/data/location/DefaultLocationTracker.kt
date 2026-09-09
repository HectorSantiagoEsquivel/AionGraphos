package org.aioncyclus.aiongraphos.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {

    override suspend fun getCurrentLocation(): Location? {

        val hasFineLocationPermission =
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocationPermission =
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (!hasFineLocationPermission && !hasCoarseLocationPermission) {
            return null
        }

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isLocationEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isLocationEnabled) {
            return null
        }


        val lastLocation = try {
            locationClient.lastLocation.await()
        } catch (e: Exception) {
            null
        }

        return lastLocation ?: fetchLocationWithUpdates()
    }

    private suspend fun fetchLocationWithUpdates(): Location? =
        withTimeoutOrNull(10.seconds) {
            suspendCancellableCoroutine { cont ->

                val request = LocationRequest.Builder(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    1_000L
                ).build()

                val callback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        locationClient.removeLocationUpdates(this)

                        if (cont.isActive) {
                            cont.resume(
                                result.lastLocation,
                                onCancellation = null
                            )
                        }
                    }
                }

                try {
                    locationClient.requestLocationUpdates(
                        request,
                        callback,
                        Looper.getMainLooper()
                    )
                } catch (e: SecurityException) {
                    // Handle missing permissions
                    if (cont.isActive) {
                        cont.resume(null, onCancellation = null)
                    }
                    return@suspendCancellableCoroutine
                }

                cont.invokeOnCancellation {
                    locationClient.removeLocationUpdates(callback)
                }
            }
        }
}