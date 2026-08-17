package org.aioncyclus.aiongraphos.ui.screens.planetdetail


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.Color
import org.aioncyclus.aiongraphos.ui.components.aspectdashboard.AspectDashboard
import org.aioncyclus.aiongraphos.ui.mapper.*
import org.aioncyclus.aiongraphos.ui.theme.ElementColour
import org.aioncyclus.aiongraphos.ui.theme.PlanetColour

import org.aioncyclus.aiongraphos.ui.components.detail.GlyphCard
import org.aioncyclus.aiongraphos.ui.components.detail.PositionCard
import org.aioncyclus.aiongraphos.ui.components.detail.ScoreCard
import org.aioncyclus.aiongraphos.ui.components.detail.SpeedCard
import org.aioncyclus.aiongraphos.ui.components.dignitychart.DignityChart


@Composable
fun PlanetDetailScreen(
    state: PlanetDetailUIState,
    modifier: Modifier = Modifier
) {
    val planetColour = PlanetColour.of(state.planet)
    val planetIconRes = iconOf(state.planet)

    PortraitView(state,planetColour,planetIconRes,modifier)

}

@Composable
private fun PortraitView(
    state: PlanetDetailUIState,
    planetColour: Color,
    planetIconRes: Int,
    modifier: Modifier = Modifier
)
{
    Column(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            )
            {
                GlyphCard(
                    planetIconRes,
                    state.isRetrograde,
                    planetColour,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )
            }


            if (state.zodiacPosition != null) {
                val signColour = ElementColour.of(state.zodiacPosition.sign)
                val signIconRes = iconOf(state.zodiacPosition.sign)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    PositionCard(
                        signIconRes,
                        state.zodiacPosition,
                        signColour,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    )
                }

            } else {
                Spacer(
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ){
                ScoreCard(
                    state.score,
                    state.strength,
                    planetColour,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ){
                SpeedCard(
                    state.planetSpeed,
                    state.speedGaugeValue,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.dignityTimeline.isNotEmpty()) {
                DignityChart(
                    timeline = state.dignityTimeline,
                    colour = planetColour,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //val columns = maxOf(1, (state.aspects.size + 1) / 2)
            val columns = 5
            AspectDashboard(
                aspects = state.aspects,
                currentPlanet = state.planet,
                columns = columns,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

