package org.aioncyclus.aiongraphos.ui.components.planetdashboard

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.R

data class PlanetCardUIState(
    val planet: Planet= Planet.SUN,
    @DrawableRes val iconRes: Int = R.drawable.ic_planet_sol,
    val strength: Double =0.0,
    val colour: Color=Color.White
)