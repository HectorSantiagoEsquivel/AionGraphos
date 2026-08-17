package org.aioncyclus.aiongraphos.ui.components.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.aioncyclus.aiongraphos.R


@Composable
fun GlyphCard(iconRes: Int,
              isRetrograde: Boolean,
              colour: Color,
              modifier: Modifier = Modifier)
{
    Card(
        shape= RoundedCornerShape(10.dp),
        modifier= modifier
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray.copy(alpha = 0.12f)
        )
    )
    {
        Box(contentAlignment = Alignment.Center,
            modifier= Modifier.fillMaxSize())
        {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = colour,
                modifier = Modifier.fillMaxSize(0.7F)
            )
            if(isRetrograde)
            {
                Icon(
                    painter = painterResource(R.drawable.ic_astro_retrograde),
                    contentDescription = "Retrograde",
                    tint = colour,
                    modifier = Modifier
                        .fillMaxSize(0.4f)
                        .align(AbsoluteAlignment.BottomRight)
                )
            }
        }
    }
}

