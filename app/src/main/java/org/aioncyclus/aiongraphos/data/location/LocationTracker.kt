package org.aioncyclus.aiongraphos.data.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}