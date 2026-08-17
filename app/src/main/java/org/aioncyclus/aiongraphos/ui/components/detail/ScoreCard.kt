package org.aioncyclus.aiongraphos.ui.components.detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.aioncyclus.aiongraphos.ui.components.gauge.ProgressGauge
import org.aioncyclus.aiongraphos.ui.theme.JetBrainsMono

@Composable
fun ScoreCard(score:Int,
              strengthPercentage: Double,
              colour: Color=Color.White,
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
            val textMeasurer = rememberTextMeasurer(0)
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                drawScoreLabel(
                    textMeasurer = textMeasurer,
                    score = score
                )

            }

            ProgressGauge(value = strengthPercentage, progressColour = colour)

        }
    }
}

private fun DrawScope.drawScoreLabel(
    textMeasurer: TextMeasurer,
    score: Int,
    colour: Color =Color.White
)
{
    val titleFontSize =with(this) {
        (size.minDimension * 0.11f).toSp()
    }
    val scoreFontSize = with(this) {
        (size.minDimension * 0.33f).toSp()
    }
    val valueText = "$score"
    val titleText="Dignity"
    val titleLayout = textMeasurer.measure(
        text = titleText,
        style = TextStyle(
            fontSize = titleFontSize,
            color = Color.White,
            textAlign = TextAlign.Start
        )
    )
    val valueLayout = textMeasurer.measure(
        text = valueText,
        style = TextStyle(
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = scoreFontSize,
            color = colour,
            textAlign = TextAlign.Center
        )
    )


    drawText(
        textLayoutResult = titleLayout,
        topLeft = Offset(
            8.dp.toPx(),
            8.dp.toPx()
        )
    )

    drawText(
        textLayoutResult = valueLayout,
        topLeft = Offset(
            x = (size.width - valueLayout.size.width) / 2f,
            y = (size.height - valueLayout.size.height) / 2f
                    + size.minDimension * 0.05f
        )
    )
}
