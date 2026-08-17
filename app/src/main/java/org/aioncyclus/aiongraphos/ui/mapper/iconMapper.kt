package org.aioncyclus.aiongraphos.ui.mapper

import androidx.annotation.DrawableRes
import org.aioncyclus.aiongraphos.R
import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.aspect.AspectType
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign

@DrawableRes
fun iconOf(planet:Planet): Int
{
    return when(planet)
    {
        Planet.MOON -> R.drawable.ic_planet_luna
        Planet.MERCURY->R.drawable.ic_planet_mercurius
        Planet.VENUS->R.drawable.ic_planet_venus
        Planet.SUN -> R.drawable.ic_planet_sol
        Planet.MARS -> R.drawable.ic_planet_mars
        Planet.JUPITER -> R.drawable.ic_planet_jupiter
        Planet.SATURN -> R.drawable.ic_planet_saturnus
        Planet.URANUS -> R.drawable.ic_planet_uranus
        Planet.NEPTUNE -> R.drawable.ic_planet_neptunus
        Planet.PLUTO -> R.drawable.ic_planet_pluto
        Planet.MEAN_NORTH_NODE -> R.drawable.ic_node_caput_draconis
        Planet.TRUE_NORTH_NODE -> R.drawable.ic_node_caput_draconis
        Planet.MEAN_SOUTH_NODE-> R.drawable.ic_node_cauda_draconis
        Planet.TRUE_SOUTH_NODE-> R.drawable.ic_node_cauda_draconis
    }
}

@DrawableRes
fun iconOf(sign: Sign): Int
{
    return when(sign)
    {
        Sign.ARIES -> R.drawable.ic_sign_aries
        Sign.TAURUS ->R.drawable.ic_sign_taurus
        Sign.GEMINI -> R.drawable.ic_sign_gemini
        Sign.CANCER -> R.drawable.ic_sign_cancer
        Sign.LEO -> R.drawable.ic_sign_leo
        Sign.VIRGO -> R.drawable.ic_sign_virgo
        Sign.LIBRA -> R.drawable.ic_sign_libra
        Sign.SCORPIO -> R.drawable.ic_sign_scorpio
        Sign.SAGITTARIUS -> R.drawable.ic_sign_sagittarius
        Sign.CAPRICORN -> R.drawable.ic_sign_capricornus
        Sign.AQUARIUS -> R.drawable.ic_sign_aquarius
        Sign.PISCES -> R.drawable.ic_sign_pisces
    }
}

@DrawableRes
fun iconOf(aspect: Aspect): Int
{
    return when(aspect.aspectType)
    {
        AspectType.CONJUNCTION -> R.drawable.ic_astro_conjunction
        AspectType.SEXTILE -> R.drawable.ic_astro_sextile
        AspectType.SQUARE -> R.drawable.ic_astro_square
        AspectType.TRINE -> R.drawable.ic_astro_trine
        AspectType.OPPOSITION -> R.drawable.ic_astro_opposition
    }
}

