package org.aioncyclus.aiongraphos.data.location

import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class AndroidReverseGeocoder @Inject constructor(
    @ApplicationContext private val context: Context
):ReverseGeocoder {

    //TODO: Tidy this class by separating the opinionated string from the geolocation function
    override suspend fun getLocationName(
        latitude: Double,
        longitude: Double
    ): String? = withContext(Dispatchers.IO) {
        try {

            val geocoder = Geocoder(
                context,
                Locale.getDefault()
            )
            geocoder
                .getFromLocation(latitude, longitude, 1)
                ?.getOrNull(0)
                ?.toLocationName()

        } catch (e: Exception) {
            null
        }
    }

    private fun Address.toLocationName(): String? {
        val locale = listOfNotNull(
            locality ?: subLocality,
            subAdminArea,
            adminArea,
            countryName
        )
        return locale
            .take(2)
            .joinToString(", ")
            .ifBlank { null }
    }
}