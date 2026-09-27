package org.aioncyclus.aiongraphos.ui.components.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.aioncyclus.aiongraphos.domain.model.aspect.Distance
import org.aioncyclus.aiongraphos.ui.components.gauge.NeedleGauge
import org.aioncyclus.aiongraphos.ui.theme.MaritimeBlue
import org.aioncyclus.aiongraphos.ui.theme.SolarGold
import org.aioncyclus.aiongraphos.ui.theme.SteelBlue

@Composable
fun SpeedCard(speed: Distance,
              speedGaugeValue: Double,
              modifier: Modifier = Modifier)
{
    val stationaryColour= MaritimeBlue
    val slowColour= SteelBlue
    val swiftColour= SolarGold
    val labelColour= MaterialTheme.colorScheme.onSurface

    Card(
        shape= RoundedCornerShape(10.dp),
        modifier= modifier
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray.copy(alpha = 0.12f)
        )
    )
    {
        Box(
            contentAlignment = Alignment.Center,
            modifier= Modifier.fillMaxSize()
        )
        {
            NeedleGauge(speed, speedGaugeValue,labelColour,stationaryColour, slowColour,swiftColour)
        }
    }
}


