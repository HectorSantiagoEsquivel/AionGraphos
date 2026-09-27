package org.aioncyclus.aiongraphos.ui.components.planetdashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun PlanetDashboard(
    state: PlanetDashboardUIState,
    columns: Int,
    onPlanetClick: (Planet) -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = state.states.chunked(columns)

    Column(
        modifier = modifier
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        rows.forEach { rowItems ->

            PlanetDashboardRow(
                items = rowItems,
                columns = columns,
                onPlanetClick = onPlanetClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PlanetDashboardRow(
    items: List<PlanetCardUIState>,
    columns: Int,
    onPlanetClick: (Planet) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                PlanetCard(
                    state = item,
                    onClick = {
                        onPlanetClick(item.planet)
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )
            }
        }

        repeat(columns - items.size) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
