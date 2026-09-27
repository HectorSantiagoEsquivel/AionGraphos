package org.aioncyclus.aiongraphos.domain.analyst

import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart
import org.aioncyclus.aiongraphos.domain.model.chart.Sect
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
//TODO:
// - This class could be an object
class SectCalculator
{
    fun determine(astroChart: AstroChart): Sect {
        val sunLongitude = astroChart.getPlanetData(Planet.SUN).planetPosition.longitude
        val ascLongitude = astroChart.housesData.angles.ascendant

        val distance = ((sunLongitude - ascLongitude + 360) % 360)

        if(distance > 180)
        {
            return Sect.DAY
        }
        else
        {
            return Sect.NIGHT
        }
    }
}