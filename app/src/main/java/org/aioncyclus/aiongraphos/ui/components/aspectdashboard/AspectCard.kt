package org.aioncyclus.aiongraphos.ui.components.aspectdashboard


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.ui.mapper.iconOf
import org.aioncyclus.aiongraphos.ui.theme.AspectColour


@Composable
fun AspectCard(aspect: Aspect,
               currentPlanet: Planet? = null,
               onClick: (() -> Unit)? = null,
               modifier: Modifier = Modifier)
{
    val aspectColour= AspectColour.of(aspect)
    val planetColour= MaterialTheme.colorScheme.onBackground
    val surfaceColour= MaterialTheme.colorScheme.surface
    Card(
        shape= RoundedCornerShape(10.dp),
        modifier= modifier
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = surfaceColour
        )
    )
    {
        Box(contentAlignment = Alignment.Center,
            modifier= Modifier.fillMaxSize())
        {
            val aspectIconRes = iconOf(aspect)

            if(currentPlanet==null)
            {
                val planetAIconRes = iconOf(aspect.planetA)
                val planetBIconRes = iconOf(aspect.planetB)

                Icon(
                    painter = painterResource(planetBIconRes),
                    contentDescription = "Planet B",
                    tint = planetColour,
                    modifier = Modifier
                        .fillMaxSize(0.4f)
                        .align(AbsoluteAlignment.BottomRight)
                )
                Icon(
                    painter = painterResource(planetAIconRes),
                    contentDescription = "Planet A",
                    tint = planetColour,
                    modifier = Modifier
                        .fillMaxSize(0.4f)
                        .align(AbsoluteAlignment.TopLeft)
                )
                Icon(
                    painter = painterResource(aspectIconRes),
                    contentDescription = "Aspect",
                    tint = aspectColour,
                    modifier = Modifier
                        .fillMaxSize(0.7f)
                )
            }
            else
            {
                val otherPlanet =
                    if (currentPlanet == aspect.planetA) {
                        aspect.planetB
                    } else {
                        aspect.planetA
                    }

                val otherPlanetIconRes = iconOf(otherPlanet)
                Icon(
                    painter = painterResource(otherPlanetIconRes),
                    contentDescription = "Other Planet",
                    tint = planetColour,
                    modifier = Modifier
                        .fillMaxSize(0.6f)
                        .align(AbsoluteAlignment.TopLeft)
                )
                Icon(
                    painter = painterResource(aspectIconRes),
                    contentDescription = "Aspect",
                    tint = aspectColour,
                    modifier = Modifier
                        .fillMaxSize(0.65f)
                        .align(AbsoluteAlignment.BottomRight)
                )
            }
        }
    }
}

