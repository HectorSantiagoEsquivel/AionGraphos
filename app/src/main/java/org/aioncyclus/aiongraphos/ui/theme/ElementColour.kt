package org.aioncyclus.aiongraphos.ui.theme

import androidx.compose.ui.graphics.Color
import org.aioncyclus.aiongraphos.domain.model.zodiac.Element
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign

object ElementColour {

    fun of(element: Element): Color
    {
        return when(element)
        {
            Element.FIRE -> Color(0xFFE53935)
            Element.AIR ->Color(0xFFFFD54F)
            Element.WATER -> Color(0xFF1E88E5)
            Element.EARTH -> Color(0xFF4CAF50)
        }
    }

    fun of(sign: Sign): Color = of(sign.element)
}