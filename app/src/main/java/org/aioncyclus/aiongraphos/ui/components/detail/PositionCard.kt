package org.aioncyclus.aiongraphos.ui.components.detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.aioncyclus.aiongraphos.domain.model.zodiac.ZodiacPosition
import org.aioncyclus.aiongraphos.ui.mapper.iconOf
import org.aioncyclus.aiongraphos.ui.theme.ElementColour
import org.aioncyclus.aiongraphos.ui.theme.JetBrainsMono

@Composable
fun PositionCard
            (zodiacPosition: ZodiacPosition,
             modifier: Modifier = Modifier)
{
    val iconRes = iconOf(zodiacPosition.sign)
    val iconColour= ElementColour.of(zodiacPosition.sign)
    val labelColour= MaterialTheme.colorScheme.onSurface
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
        val textMeasurer = rememberTextMeasurer(0)
        Box(contentAlignment = Alignment.Center,
            modifier= Modifier.fillMaxSize())
        {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = iconColour,
                modifier = Modifier.fillMaxSize(0.7F)
            )
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
            {
                drawPositionLabel(textMeasurer,
                    zodiacPosition.degreeInSign,
                    zodiacPosition.minuteInDegree,
                    labelColour)
            }
        }
    }
}

private fun DrawScope.drawPositionLabel(
    textMeasurer: TextMeasurer,
    degree: Int,
    minute: Int,
    colour: Color
)
{

    val fontSize = with(this) {
        (size.minDimension * 0.12f).toSp()
    }
    val valueText = "${degree}°${minute.toString().padStart(2, '0')}'"

    val valueLayout = textMeasurer.measure(
        text = valueText,
        style = TextStyle(
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            color = colour,
            textAlign = TextAlign.Center
        )
    )


    drawText(
        textLayoutResult = valueLayout,
        topLeft = Offset(
            x = size.width / 2f - valueLayout.size.width / 2.5f,  // Center.x - width/2.5
            y = size.height / 2f + size.width / 2f - valueLayout.size.height - 8.dp.toPx()
            // Center.y + radius - height - 8dp
        )
    )
}

