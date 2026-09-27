package org.aioncyclus.aiongraphos.ui.theme

import androidx.compose.ui.graphics.Color
import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.aspect.AspectType
import org.aioncyclus.aiongraphos.domain.model.zodiac.Element

object AspectColour {

    fun of(aspect: Aspect): Color
    {
        return when(aspect.aspectType)
        {
            AspectType.OPPOSITION, AspectType.SQUARE -> RubyRed
            AspectType.TRINE, AspectType.SEXTILE -> SapphireBlue
            AspectType.CONJUNCTION->CitrineYellow
            else -> EmeraldGreen
        }
    }
}