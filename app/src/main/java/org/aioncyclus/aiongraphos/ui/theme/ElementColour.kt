package org.aioncyclus.aiongraphos.ui.theme

import androidx.compose.ui.graphics.Color
import org.aioncyclus.aiongraphos.domain.model.zodiac.Element
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign

object ElementColour {

    fun of(element: Element): Color
    {
        return when(element)
        {
            Element.FIRE  -> RubyRed
            Element.AIR   -> CitrineYellow
            Element.WATER -> SapphireBlue
            Element.EARTH -> EmeraldGreen
        }
    }

    fun of(sign: Sign): Color = of(sign.element)
}