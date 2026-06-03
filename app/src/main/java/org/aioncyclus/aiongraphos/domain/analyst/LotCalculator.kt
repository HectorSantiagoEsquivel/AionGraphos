package org.aioncyclus.aiongraphos.domain.analyst

import org.aioncyclus.aiongraphos.domain.calculator.ZodiacMapper
import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart
import org.aioncyclus.aiongraphos.domain.model.chart.Sect
import org.aioncyclus.aiongraphos.domain.model.lot.LotData
import org.aioncyclus.aiongraphos.domain.model.lot.LotPoint
import org.aioncyclus.aiongraphos.domain.model.lot.LotType


class LotCalculator
{
    fun calculateLots(astroChart: AstroChart,chartSect: Sect, lots: List<LotType>): List<LotData>
    {
        val lotsData=buildList {
            for(lot in lots)
            {
                add(calculateLot(astroChart,chartSect,lot))
            }
        }
        return lotsData
    }

    fun calculateLot(astroChart : AstroChart, chartSect: Sect, lotType: LotType): LotData {


        val formula = if (chartSect == Sect.DAY) {
            lotType.diurnalFormula
        } else {
            lotType.nocturnalFormula
        }

        var lotLongitude = 0.0

        lotLongitude=
                    resolvePointLongitude(formula.point1,astroChart,chartSect) +
                    resolvePointLongitude(formula.point2,astroChart,chartSect) -
                    resolvePointLongitude(formula.point3,astroChart,chartSect)

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
        astroChart: AstroChart,
        chartSect: Sect
    ): Double {
        return when (point) {
            is LotPoint.Ascendant -> astroChart.housesData.angles.ascendant
            is LotPoint.Midheaven -> astroChart.housesData.angles.midheaven
            is LotPoint.PlanetPoint -> astroChart.getPlanetData(point.planet).planetPosition.longitude
            is LotPoint.BaseLot -> calculateLot(astroChart,chartSect, point.lotType).longitude
        }
    }
}