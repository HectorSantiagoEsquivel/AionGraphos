package org.aioncyclus.aiongraphos.domain.model.zodiac

import org.aioncyclus.aiongraphos.domain.calculator.ZodiacMapper


enum class FixedStar(
    val longitude: Double,

)
{
    REGULUS(
        149.6667, // 29°40' Leo
    ),
    SPICA(
        203.6667, // 23°40' Libra
    ),
    ALGOL(
        56.0667, // 26°04' Taurus
    );
    val zodiacPosition: ZodiacPosition
        get() = ZodiacMapper.fromLongitude(longitude)

}