package org.aioncyclus.aiongraphos.ui.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.aioncyclus.aiongraphos.data.repository.ChartRepository
import org.aioncyclus.aiongraphos.data.repository.LocationRepository
import org.aioncyclus.aiongraphos.domain.model.chart.ChartContext
import org.aioncyclus.aiongraphos.domain.model.dignity.TraditionalDignities
import org.aioncyclus.aiongraphos.domain.model.dignity.scoreRange.TraditionalPlanetScoreRange
import org.aioncyclus.aiongraphos.domain.model.lot.LotType
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.ui.components.planetdashboard.PlanetCardUIState
import org.aioncyclus.aiongraphos.ui.components.planetdashboard.PlanetDashBoardUIState
import org.aioncyclus.aiongraphos.ui.mapper.iconOf
import org.aioncyclus.aiongraphos.ui.theme.PlanetColour
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val chartRepository: ChartRepository,
    private val locationRepository: LocationRepository
): ViewModel()
{

    var state by mutableStateOf(PlanetDashBoardUIState())
        private set

    init {
        load()
    }

    fun load()
    {
        viewModelScope.launch {
            val location = locationRepository.getCurrentLocation()?:
            locationRepository.useCurrentLocation()?:
            return@launch

            val planets=listOf<Planet>(
                Planet.MOON,
                Planet.MERCURY,
                Planet.VENUS,
                Planet.SUN,
                Planet.MARS,
                Planet.JUPITER,
                Planet.SATURN,
                Planet.URANUS,
                Planet.NEPTUNE,
                Planet.PLUTO
            )
            val lots=listOf<LotType>(LotType.Fortune, LotType.Spirit, LotType.Eros, LotType.Exaltation)
            val dignitySystem= TraditionalDignities()

            if(location!=null)
            {
                val chartContext=
                    ChartContext(Instant.now(), location)
                val chart=chartRepository.refresh(planets,
                    lots,
                    chartContext,
                    dignitySystem)
                val planetStates = mutableListOf<PlanetCardUIState>()

                for (planetData in chart.chart.planetaryData)
                {
                    val score = chart.analysis.conditions
                        .first { it.planet == planetData.planet }
                        .score

                    val strengthPercentage= calculateStrengthPercentage(planetData.planet,score)
                    val planetColour= PlanetColour.of(planetData.planet)
                    val iconRes=iconOf(planetData.planet)

                    planetStates.add(
                        PlanetCardUIState(
                            planet = planetData.planet,
                            iconRes = iconRes,
                            strength = strengthPercentage,
                            planetColour
                        )
                    )
                }
                state = PlanetDashBoardUIState(planetStates,location.name)
            }

        }

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

}