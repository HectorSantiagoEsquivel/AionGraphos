package org.aioncyclus.aiongraphos.ui.components.planetdashboard

data class PlanetDashBoardUIState(
    val states:List<PlanetCardUIState> = emptyList<PlanetCardUIState>(),
    val title:String=""
)