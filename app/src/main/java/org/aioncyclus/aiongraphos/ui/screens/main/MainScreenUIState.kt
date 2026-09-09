package org.aioncyclus.aiongraphos.ui.screens.main

import org.aioncyclus.aiongraphos.ui.components.chart.ChartUIState
import org.aioncyclus.aiongraphos.ui.components.planetdashboard.PlanetDashboardUIState

data class MainScreenUIState(
    val planetDashboardUIState: PlanetDashboardUIState?=null,
    val chartUIState: ChartUIState?=null,
    val title:String="",
    val isLoading:Boolean = true,
    val error: String? = null
)
