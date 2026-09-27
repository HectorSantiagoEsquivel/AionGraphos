package org.aioncyclus.aiongraphos.domain.model.location

import java.time.ZoneId

data class Location(
    val name: String,
    val zoneID: ZoneId,
    val latitude: Double,
    val longitude: Double
)