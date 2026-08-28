package org.aioncyclus.aiongraphos.data.location
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

import javax.inject.Singleton

class LocationNameResolver @Inject constructor(
    val reverseGeocoder: AndroidReverseGeocoder
) {

    suspend fun provideChartService (
        latitude: Double,
        longitude: Double
    ):String?
    {
        val localeName= reverseGeocoder.getLocationName(latitude,longitude)
        return localeName

    }
}