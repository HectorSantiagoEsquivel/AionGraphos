package org.aioncyclus.aiongraphos.domain.calculator


import org.aioncyclus.aiongraphos.domain.model.aspect.Distance
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign.*
import org.aioncyclus.aiongraphos.domain.model.zodiac.ZodiacPosition
import kotlin.math.roundToInt

object ZodiacMapper
{
    fun fromLongitude(longitude: Double): ZodiacPosition
    {
        val normalisedLongitude= normaliseLongitude(longitude)

        val sign=signFromLongitude(normalisedLongitude)
        val degreeInSign=degreeFromLongitude(normalisedLongitude)
        val minuteInSign=minuteFromLongitude(normalisedLongitude)

        return ZodiacPosition(sign,degreeInSign,minuteInSign)
    }

    private fun normaliseLongitude(longitude: Double): Double {
        return ((longitude % 360) + 360) % 360
    }

    private fun signFromLongitude(longitude:Double): Sign
    {
        val signs = arrayOf(ARIES, TAURUS, GEMINI, CANCER,
            LEO, VIRGO, LIBRA, SCORPIO,
            SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES)

        return signs[(longitude/30).toInt()]
    }

    private fun degreeFromLongitude(longitude: Double): Int
    {
        return (longitude%30).toInt()
    }

    private fun minuteFromLongitude(longitude: Double): Int
    {
        val decimalDegrees=longitude%30
        val deg = decimalDegrees.toInt()
        val minFull = (decimalDegrees - deg) * 60.0
        return minFull.toInt()
    }

    fun longitudeToDegrees(value: Double): Distance {
        val normalised = kotlin.math.abs(value)

        val degree = normalised.toInt()

        val minute = ((normalised - degree) * 60.0)
            .roundToInt()

        return Distance(degree, minute)
    }
}