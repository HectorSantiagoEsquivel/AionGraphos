package org.aioncyclus.aiongraphos.data.location

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ReverseGeocoder{
    suspend fun getLocationName(
        latitude: Double,
        longitude: Double
    ):String?
}