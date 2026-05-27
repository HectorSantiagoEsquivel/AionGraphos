package org.aioncyclus.aiongraphos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import org.aioncyclus.aiongraphos.data.ephemeris.SwissEphemerisProvider
import org.aioncyclus.aiongraphos.data.ephemeris.SwissEphemerisCalculator
import org.aioncyclus.aiongraphos.domain.calculator.ChartCalculator
import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart

import org.aioncyclus.aiongraphos.domain.calculator.HousesCalculator
import org.aioncyclus.aiongraphos.domain.calculator.PlanetCalculator
import org.aioncyclus.aiongraphos.domain.model.chart.ChartContext
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.ui.theme.AiongraphosTheme


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.aioncyclus.aiongraphos.domain.model.chart.AnalysedChart
import org.aioncyclus.aiongraphos.domain.model.lot.LotType


import org.aioncyclus.aiongraphos.domain.model.planet.Planet.*
import org.aioncyclus.aiongraphos.domain.service.ChartService
import java.time.Instant
import java.time.ZoneId

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val chartService=ChartService(this)
        val planets=listOf<Planet>(MOON,MERCURY,VENUS,SUN,MARS,JUPITER,SATURN,URANUS,NEPTUNE,PLUTO)
        val lots=listOf<LotType>(LotType.Fortune, LotType.Spirit, LotType.Eros, LotType.Exaltation)

        val chartContext= ChartContext(Instant.now(),ZoneId.of("America/Argentina/Buenos_Aires"),-34.0,-58.0)

        val analysedChart=chartService.calculateAnalysedChart(planets,lots,chartContext)


        setContent {
            AiongraphosTheme {
                ChartDebugScreen(analysedChart)
            }
        }
    }
}


@Composable
fun PlanetPositionScreen(resultText: String) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = resultText, style = MaterialTheme.typography.bodyLarge)
    }
}


@Composable
fun ChartDebugScreen(chart: AnalysedChart) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "Planetas",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            LazyColumn {
                items(chart.chart.planetaryData) { planet ->
                    Text(
                        text = "${planet.planet.planetName}: " +
                                "${planet.zodiacPosition.degreeInSign}°" +
                                "${planet.zodiacPosition.minuteInDegree}' " +
                                "${planet.zodiacPosition.sign.signName}",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }


        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.outline
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "Casas",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn {
                items(chart.chart.housesData.houses) { house ->
                    Text(
                        text = "Casa ${house.number}: " +
                                "${house.zodiacPosition.degreeInSign}°" +
                                "${house.zodiacPosition.minuteInDegree}' " +
                                "${house.zodiacPosition.sign.signName}",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.outline
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "Partes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn {
                items(chart.analysis.lots) { lot ->
                    Text(
                        text = "${lot.lotType.name}: " +
                                "${lot.zodiacPosition.degreeInSign}°" +
                                "${lot.zodiacPosition.minuteInDegree}' " +
                                "${lot.zodiacPosition.sign.signName}",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.outline
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "Aspectos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn {
                items(chart.analysis.aspects) { aspect ->
                    Text(
                        text = "${aspect.aspectType}: " +
                                "between ${aspect.planetA.planetName} " +
                                "and ${aspect.planetB.planetName}' " +
                                "with an orb of ${aspect.separation.degree}° ${aspect.separation.minute}'",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AiongraphosTheme {
        Text(
            text="Hello World",
            color= Color.Cyan,
            fontSize = 40.sp,
            modifier= Modifier.background(Color.Red)


        )
    }
}
