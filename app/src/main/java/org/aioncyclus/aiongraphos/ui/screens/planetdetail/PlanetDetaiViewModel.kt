package org.aioncyclus.aiongraphos.ui.screens.planetdetail


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.aioncyclus.aiongraphos.data.repository.ChartRepository
import org.aioncyclus.aiongraphos.domain.calculator.ZodiacMapper
import org.aioncyclus.aiongraphos.domain.model.dignity.scoreRange.TraditionalPlanetScoreRange
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.aioncyclus.aiongraphos.domain.model.chart.AnalysedChart
import org.aioncyclus.aiongraphos.domain.model.zodiac.ZodiacPosition
import org.aioncyclus.aiongraphos.ui.components.dignitychart.PlanetDignitySample
import org.aioncyclus.aiongraphos.ui.components.planetdashboard.PlanetCardUIState
import org.aioncyclus.aiongraphos.ui.mapper.iconOf
import org.aioncyclus.aiongraphos.ui.theme.PlanetColour
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class PlanetDetaiViewModel @Inject constructor(
    private val chartRepository: ChartRepository
): ViewModel()
{

    var state by mutableStateOf(PlanetDetailUIState())
        private set

    fun load(
        planet: Planet,
    ) {
        val analysedChart = chartRepository.getCurrentChart()
        val timeline = chartRepository.getTimeline()
        if (analysedChart == null) {
            return
        }
        val planetData = analysedChart.getPlanetData(planet)
        val score = analysedChart.getPlanetCondition(planet)

        val strengthPercentage = calculateStrengthPercentage(planet, score)
        val speed = ZodiacMapper.longitudeToDegrees(planetData.planetPosition.speedLongitude)
        val gaugeSpeed = speedToGauge(planet, speed.absolute)
        val zodiacPosition = planetData.zodiacPosition
        val dignityTimeline = calculateDignityTimeline(planet, timeline)
        val aspects= analysedChart.getPlanetAspects(planet)

        state = PlanetDetailUIState(
            planet,
            planetData.isRetrograde,
            zodiacPosition,
            speed,
            gaugeSpeed,
            score,
            strengthPercentage,
            aspects,
            dignityTimeline
        )
    }


    private fun calculateDignityTimeline(planet:Planet,
                                         timeline:List<AnalysedChart>):List<PlanetDignitySample>
    {
        val timeline=buildList {
            for(chart in timeline)
            {
                val instant=chart.chart.chartContext.contextDate
                val zoneId=chart.chart.chartContext.location.zoneID
                val zodiacPosition= chart.getPlanetData(planet).zodiacPosition
                val score=chart.getPlanetCondition(planet)
                val strengthPercentage= calculateStrengthPercentage(planet,score)
                add(PlanetDignitySample(instant, zoneId, zodiacPosition, score, strengthPercentage))
            }
        }

        return timeline
    }


    private fun calculateStrengthPercentage(planet: Planet, score: Int):Double
    {
        val planetScoreRange= TraditionalPlanetScoreRange.scoreRangeOf(planet)
        val minRangeDifference=planetScoreRange.min *-1.0
        val normalisedMin=planetScoreRange.min + minRangeDifference
        val normalisedMax=planetScoreRange.max + minRangeDifference
        val normalisedScore=score + minRangeDifference
        var strengthPercentage =100.0
        if(normalisedMax!=normalisedMin)
        {
            strengthPercentage= (normalisedScore/normalisedMax)*100.0
        }
        return strengthPercentage
    }

    fun speedToGauge(planet: Planet, speed: Double): Double {

        val mean=planet.meanSpeed
        val max = mean * 2.0

        return when {
            speed <= mean -> {
                (speed / mean) * 50.0
            }
            else -> {
                50.0 + ((speed - mean) / (max - mean)) * 50.0
            }
        }.coerceIn(0.0, 100.0)
    }
}