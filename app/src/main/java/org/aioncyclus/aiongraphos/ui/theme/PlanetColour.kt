package org.aioncyclus.aiongraphos.ui.theme

import androidx.compose.ui.graphics.Color
import org.aioncyclus.aiongraphos.domain.model.planet.Planet

object PlanetColour {

    fun of(planet: Planet): Color
    {
        return when(planet)
        {
            Planet.SUN -> Color(0xFFFFC107)
            Planet.MOON -> Color(0xFFECEFF1)
            Planet.MERCURY -> Color(0xFF78909C)
            Planet.VENUS -> Color(0xFFFF8A65)
            Planet.MARS -> Color(0xFFEF5350)
            Planet.JUPITER -> Color(0xFFFFA726)
            Planet.SATURN -> Color(0xFFFFEE58)
            Planet.URANUS -> Color(0xFF4DD0E1)
            Planet.NEPTUNE -> Color(0xFF5C6BC0)
            Planet.PLUTO -> Color(0xFF8D6E63)
            else ->  Color(0xFFA0B2A0)
        }
    }
}