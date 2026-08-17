package org.aioncyclus.aiongraphos.ui.components.planetdashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.aioncyclus.aiongraphos.ui.components.gauge.ProgressGauge

@Composable
fun PlanetCard(
    state: PlanetCardUIState,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .aspectRatio(1f)
            .let {
                if (onClick != null) {
                    it.clickable(onClick = onClick)
                } else {
                    it
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray.copy(alpha = 0.12f)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            ProgressGauge(state.strength, progressColour = state.colour)
            Icon(
                painter = painterResource(state.iconRes),
                contentDescription = null,
                tint = state.colour,
                modifier = Modifier.fillMaxSize(0.33f)
            )
        }
    }
}

