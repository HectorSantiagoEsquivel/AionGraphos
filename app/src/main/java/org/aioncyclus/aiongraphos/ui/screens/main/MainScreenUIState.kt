package org.aioncyclus.aiongraphos.ui.screens.main

import org.aioncyclus.aiongraphos.ui.components.chart.ChartUIState
import org.aioncyclus.aiongraphos.ui.components.planetdashboard.PlanetDashboardUIState
import java.time.Instant

data class MainScreenUIState(
    val planetDashboardUIState: PlanetDashboardUIState?=null,
    val chartUIState: ChartUIState?=null,
    val chartInstant: Instant = Instant.now(),
    val title:String="",
    val isLoading:Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)
