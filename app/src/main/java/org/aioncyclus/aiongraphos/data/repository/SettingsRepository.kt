package org.aioncyclus.aiongraphos.data.repository

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import org.aioncyclus.aiongraphos.data.location.AndroidReverseGeocoder
import org.aioncyclus.aiongraphos.data.location.LocationTracker
import org.aioncyclus.aiongraphos.data.location.TimeZoneResolver
import org.aioncyclus.aiongraphos.data.model.ChartSettings
import org.aioncyclus.aiongraphos.domain.model.location.Location
import java.io.File
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.io.bufferedReader
import kotlin.io.readText
import kotlin.io.use


@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
)
{
    private val settingsFile = File(context.filesDir, "chart_settings.json")

    private val json = Json { ignoreUnknownKeys = true }
    private var currentSettings: ChartSettings?=null

    fun loadSettings(): ChartSettings {
        try {
            val jsonString = settingsFile.readText()
            val settings = json.decodeFromString<ChartSettings>(jsonString)
            currentSettings = settings
            return settings
        }
        catch(e: SerializationException) {
            val jsonString = context.assets
                .open("settings/chart_settings.json")
                .bufferedReader()
                .use { it.readText() }
            val settings = json.decodeFromString<ChartSettings>(jsonString)

            currentSettings = settings
            return settings
        }
    }

    fun saveSettings(chartSettings: ChartSettings) {
        val jsonString: String = json.encodeToString(chartSettings)
        println("DebugSettings :"+jsonString)
        File(context.filesDir, "chart_settings.json").writeText(jsonString)
    }

    fun getCurrentSettings(): ChartSettings? = currentSettings

}


