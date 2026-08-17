package org.aioncyclus.aiongraphos.data.repository

import org.aioncyclus.aiongraphos.domain.model.chart.AnalysedChart
import org.aioncyclus.aiongraphos.domain.model.chart.ChartContext
import org.aioncyclus.aiongraphos.domain.model.dignity.DignitySystem
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem
import org.aioncyclus.aiongraphos.domain.model.lot.LotType
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.service.ChartService
import java.time.Instant
import java.time.Duration
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ChartRepository @Inject constructor(
    private val chartService: ChartService
)
{
    private var analysedChart: AnalysedChart?=null
    private var timeline = emptyList<AnalysedChart>()

    fun refresh(planets:List<Planet>,
                lots:List<LotType>,
                chartContext: ChartContext,
                dignitySystem: DignitySystem,
                node: Planet = Planet.MEAN_NORTH_NODE,
                houseSystem: HouseSystem = HouseSystem.PLACIDUS
    )
    : AnalysedChart {

        val chart = chartService.calculateAnalysedChart(
            planets,
            lots,
            chartContext,
            dignitySystem,
            node,
            houseSystem
        )
        analysedChart=chart
        timeline=calculateTimeline(
            planets,
            lots,
            chartContext,
            dignitySystem,
            node,
            houseSystem)
        return chart
    }

    private fun calculateTimeline(planets:List<Planet>,
                                      lots:List<LotType>,
                                      currentChartContext: ChartContext,
                                      dignitySystem: DignitySystem,
                                      node: Planet = Planet.MEAN_NORTH_NODE,
                                      houseSystem: HouseSystem = HouseSystem.PLACIDUS
    ):List<AnalysedChart>
    {

        val timeline=buildList{

            for (i in 0..30)
            {
                val futureDate = currentChartContext.contextDate.plus(Duration.ofDays(i.toLong()))
                val futureChartContext =
                    currentChartContext.copy(contextDate = futureDate)
                add(chartService.calculateAnalysedChart(
                    planets,
                    lots,
                    futureChartContext,
                    dignitySystem,
                    node,
                    houseSystem
                ))
            }

        }
        return timeline
    }

    fun getCurrentChart(): AnalysedChart?
    {
        return analysedChart
    }
    fun getTimeline(): List<AnalysedChart>
    {
        return timeline
    }



}