package org.aioncyclus.aiongraphos.domain.analyst

import org.aioncyclus.aiongraphos.domain.calculator.ZodiacMapper
import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart
import org.aioncyclus.aiongraphos.domain.model.lot.LotData
import org.aioncyclus.aiongraphos.domain.model.lot.LotPoint
import org.aioncyclus.aiongraphos.domain.model.lot.LotType
import org.aioncyclus.aiongraphos.domain.model.planet.Planet

class LotCalculator
{
    fun calculateLots(astroChart: AstroChart, lots: List<LotType>): List<LotData>
    {
        val lotsData=buildList {
            for(lot in lots)
            {
                add(calculateLot(astroChart,lot))
            }
        }
        return lotsData
    }

    fun calculateLot(astroChart : AstroChart, lotType: LotType): LotData {

        val isDayChart = determineDayChart(astroChart)

        val formula = if (isDayChart) {
            lotType.diurnalFormula
        } else {
            lotType.nocturnalFormula
        }

        println("AstroDebug Day chart: $isDayChart")
        println("AstroDebug Point1: ${resolvePointLongitude(formula.point1, astroChart)}")
        println("AstroDebug Point2: ${resolvePointLongitude(formula.point2, astroChart)}")
        println("AstroDebug Point3: ${resolvePointLongitude(formula.point3, astroChart)}")
        println("AstroDebug Calculating lot: ${lotType.name}")

        var lotLongitude = 0.0

        lotLongitude=
            resolvePointLongitude(formula.point1,astroChart) +
                    resolvePointLongitude(formula.point2,astroChart) -
                    resolvePointLongitude(formula.point3,astroChart)

        lotLongitude = ((lotLongitude % 360) + 360) % 360

        println("AstroDebug Raw longitude: $lotLongitude")

        return LotData(
            lotType = lotType,
            longitude = lotLongitude,
            zodiacPosition = ZodiacMapper.fromLongitude(lotLongitude)
        )
    }

    private fun resolvePointLongitude(
        point: LotPoint,
        astroChart: AstroChart
    ): Double {
        return when (point) {
            is LotPoint.Ascendant -> astroChart.housesData.angles.ascendant
            is LotPoint.Midheaven -> astroChart.housesData.angles.midheaven
            is LotPoint.PlanetPoint -> astroChart.getPlanetData(point.planet).planetPosition.longitude
            is LotPoint.BaseLot -> calculateLot(astroChart, point.lotType).longitude
        }
    }

    private fun determineDayChart(astroChart: AstroChart): Boolean {
        val sunLongitude = astroChart.getPlanetData(Planet.SUN).planetPosition.longitude
        val ascLongitude = astroChart.housesData.angles.ascendant

        val distance = ((sunLongitude - ascLongitude + 360) % 360)

        return distance > 180
    }
}