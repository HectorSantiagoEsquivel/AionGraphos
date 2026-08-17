package org.aioncyclus.aiongraphos.ui.navigation

import org.aioncyclus.aiongraphos.domain.model.planet.Planet

sealed class AppScreen(val route: String) {
    object Main : AppScreen("main")
    object PlanetDetail : AppScreen("planet_detail/{planet}")

    fun planetRoute(planet: Planet): String =
        "planet_detail/${planet.name}"
}