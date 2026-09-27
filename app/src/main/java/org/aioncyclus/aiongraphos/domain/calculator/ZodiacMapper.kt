package org.aioncyclus.aiongraphos.domain.calculator


import org.aioncyclus.aiongraphos.domain.model.aspect.Distance
import org.aioncyclus.aiongraphos.domain.model.zodiac.Decan
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign.*
import org.aioncyclus.aiongraphos.domain.model.zodiac.ZodiacPosition
import kotlin.math.roundToInt

object ZodiacMapper
{
    fun fromLongitude(longitude: Double): ZodiacPosition
    {
        val normalisedLongitude= normaliseLongitude(longitude)
        val decan=decanFromLongitude(normalisedLongitude)
        val sign=signFromLongitude(normalisedLongitude)
        val degreeInSign=degreeFromLongitude(normalisedLongitude)
        val minuteInSign=minuteFromLongitude(normalisedLongitude)

        return ZodiacPosition(sign,degreeInSign,minuteInSign,decan)
    }

    fun normaliseLongitude(longitude: Double): Double {
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

    private fun decanFromLongitude(longitude: Double): Decan
    {
        return DecanMapper.decanFromLongitude(longitude)
    }


    fun longitudeToDegrees(value: Double): Distance {
        val absoluteValue  = kotlin.math.abs(value)

        val degree = absoluteValue .toInt()

        val minute = ((absoluteValue  - degree) * 60.0)
            .roundToInt()

        return Distance(degree, minute, absoluteValue)
    }
}