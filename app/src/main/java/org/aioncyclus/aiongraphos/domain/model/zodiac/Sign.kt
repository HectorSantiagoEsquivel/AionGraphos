package org.aioncyclus.aiongraphos.domain.model.zodiac

/**
 * Represents the twelve zodiac signs, each spanning 30 degrees of the 360-degree ecliptic.
 *
 * @property symbol The Unicode astrological glyph representing the sign
 * @property signName The name of the zodiac sign
 *
 */
enum class Sign(
    val symbol: String,
    val signName: String
) {
    ARIES("♈", "Aries"),
    TAURUS("♉", "Taurus"),
    GEMINI("♊", "Gemini"),
    CANCER("♋", "Cancer"),
    LEO("♌", "Leo"),
    VIRGO("♍", "Virgo"),
    LIBRA("♎", "Libra"),
    SCORPIO("♏", "Scorpio"),
    SAGITTARIUS("♐", "Sagittarius"),
    CAPRICORN("♑", "Capricorn"),
    AQUARIUS("♒", "Aquarius"),
    PISCES("♓", "Pisces")
}