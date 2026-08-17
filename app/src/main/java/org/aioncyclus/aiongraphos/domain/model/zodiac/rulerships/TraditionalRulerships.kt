package org.aioncyclus.aiongraphos.domain.model.zodiac.rulerships

import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign

object TraditionalRulerships: RulershipSystem {

    override val rulerships = mapOf(
        Planet.SUN to EssentialRulerships(
            domiciles = listOf(Sign.LEO),
            exaltation = Sign.ARIES
        ),
        Planet.MOON to EssentialRulerships(
            domiciles = listOf(Sign.CANCER),
            exaltation = Sign.TAURUS
        ),
        Planet.MERCURY to EssentialRulerships(
            domiciles = listOf(Sign.GEMINI, Sign.VIRGO),
            exaltation = Sign.VIRGO
        ),
        Planet.VENUS to EssentialRulerships(
            domiciles = listOf(Sign.TAURUS, Sign.LIBRA),
            exaltation = Sign.PISCES
        ),
        Planet.MARS to EssentialRulerships(
            domiciles = listOf(Sign.ARIES, Sign.SCORPIO),
            exaltation = Sign.CAPRICORN
        ),
        Planet.JUPITER to EssentialRulerships(
            domiciles = listOf(Sign.SAGITTARIUS, Sign.PISCES),
            exaltation = Sign.CANCER
        ),
        Planet.SATURN to EssentialRulerships(
            domiciles = listOf(Sign.CAPRICORN, Sign.AQUARIUS),
            exaltation = Sign.LIBRA
        ),
        Planet.URANUS to EssentialRulerships(
            domiciles = emptyList(),
            exaltation = null
        ),
        Planet.NEPTUNE to EssentialRulerships(
            domiciles = emptyList(),
            exaltation = null
        ),
        Planet.PLUTO to EssentialRulerships(
            domiciles = emptyList(),
            exaltation = null
        ),
        Planet.MEAN_NORTH_NODE to EssentialRulerships(
            domiciles = emptyList(),
            exaltation = Sign.GEMINI
        ),
        Planet.TRUE_NORTH_NODE to EssentialRulerships(
            domiciles = emptyList(),
            exaltation = Sign.GEMINI
        ),
        Planet.MEAN_SOUTH_NODE to EssentialRulerships(
            domiciles = emptyList(),
            exaltation = Sign.SAGITTARIUS
        ),
        Planet.TRUE_SOUTH_NODE to EssentialRulerships(
            domiciles = emptyList(),
            exaltation = Sign.SAGITTARIUS
        )

    )
}