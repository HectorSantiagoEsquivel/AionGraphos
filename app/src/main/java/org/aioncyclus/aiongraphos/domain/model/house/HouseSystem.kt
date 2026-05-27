package org.aioncyclus.aiongraphos.domain.model.house

import swisseph.SweConst

/**
 * Represents an astrological house system for use in astrological calculations
 *
 * @property seId Swiss Ephemeris constant identifier
 * @property systemName The name of the house system
 *
 * @see swisseph.SweConst
 */
enum class HouseSystem(
    val seId: Int,
    val systemName: String)
{
    WHOLE(SweConst.SE_HSYS_WHOLE_SIGN, "Whole Sign"),
    PLACIDUS(SweConst.SE_HSYS_PLACIDUS, "Placidus"),
    EQUAL(SweConst.SE_HSYS_EQUAL,"Equal Sign")
}