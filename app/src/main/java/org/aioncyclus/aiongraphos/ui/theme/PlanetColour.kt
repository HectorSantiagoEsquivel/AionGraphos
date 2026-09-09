package org.aioncyclus.aiongraphos.ui.theme

import androidx.compose.ui.graphics.Color
import org.aioncyclus.aiongraphos.domain.model.planet.Planet

object PlanetColour {

    fun of(planet: Planet): Color
    {
        return when(planet)
        {
            Planet.SUN     -> Color(0xFFFFC857) // warm solar gold
            Planet.MOON    -> Color(0xFFE5E1D8) // warm moonstone
            Planet.MERCURY -> Color(0xFF9BA6A8) // silver-grey
            Planet.VENUS   -> Color(0xFFE5A07A) // copper/rose
            Planet.MARS    -> Color(0xFFD94A45) // deep vermilion
            Planet.JUPITER -> Color(0xFFD58A4A) // ochre/copper
            Planet.SATURN  -> Color(0xFFC7B56A) // muted antique gold
            Planet.URANUS  -> Color(0xFF55C8C4) // turquoise
            Planet.NEPTUNE -> Color(0xFF6678C9) // indigo-blue
            Planet.PLUTO   -> Color(0xFF9A7F76) // dusty mauve-brown
            else           -> Color(0xFFA0B2A0)
        }
    }
}