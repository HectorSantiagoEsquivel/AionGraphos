package org.aioncyclus.aiongraphos.ui.components.aspectdashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.ui.components.planetdashboard.PlanetCard
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun AspectDashboard(
    aspects: List<Aspect>,
    columns: Int,
    modifier: Modifier = Modifier,
    currentPlanet: Planet? = null,
) {

    val rows = aspects.chunked(columns)

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        rows.forEach { rowItems ->
            AspectDashboardRow(
                items = rowItems,
                columns = columns,
                currentPlanet = currentPlanet,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun AspectDashboardRow(
    items: List<Aspect>,
    columns: Int,
    modifier: Modifier = Modifier,
    currentPlanet: Planet? = null,
    //onPlanetClick: (Planet) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                AspectCard(
                    aspect = item,
                    /*onClick = {
                        onPlanetClick(item.planet)
                    },*/
                    currentPlanet=currentPlanet,
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
