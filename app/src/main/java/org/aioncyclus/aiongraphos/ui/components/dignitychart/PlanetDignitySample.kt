package org.aioncyclus.aiongraphos.ui.components.dignitychart

import org.aioncyclus.aiongraphos.domain.model.zodiac.ZodiacPosition
import java.time.Instant
import java.time.ZoneId

data class PlanetDignitySample(
    val instant: Instant,
    val zoneId: ZoneId,
    val zodiacPosition: ZodiacPosition,
    val score: Int,
    val strength: Double
)