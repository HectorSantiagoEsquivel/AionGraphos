package org.aioncyclus.aiongraphos.domain.model.zodiac

/**
 * Represents the twelve zodiac signs, each spanning 30 degrees of the 360-degree ecliptic.
 *
 * @property symbol The Unicode astrological glyph representing the sign
 * @property signName The name of the zodiac sign
 * @property element The element of the zodiac sign
 *
 */
enum class Sign(
    val symbol: String,
    val signName: String,
    val element: Element
) {
    ARIES("♈", "Aries", Element.FIRE),
    TAURUS("♉", "Taurus", Element.EARTH),
    GEMINI("♊", "Gemini",Element.AIR),
    CANCER("♋", "Cancer",Element.WATER),
    LEO("♌", "Leo",Element.FIRE),
    VIRGO("♍", "Virgo",Element.EARTH),
    LIBRA("♎", "Libra",Element.AIR),
    SCORPIO("♏", "Scorpio",Element.WATER),
    SAGITTARIUS("♐", "Sagittarius",Element.FIRE),
    CAPRICORN("♑", "Capricorn",Element.EARTH),
    AQUARIUS("♒", "Aquarius",Element.AIR),
    PISCES("♓", "Pisces",Element.WATER);

    fun opposite(): Sign {
        return Sign.entries[(ordinal + 6) % 12]
    }
}

