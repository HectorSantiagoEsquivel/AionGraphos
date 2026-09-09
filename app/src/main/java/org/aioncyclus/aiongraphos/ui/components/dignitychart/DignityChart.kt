package org.aioncyclus.aiongraphos.ui.components.dignitychart

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider
import kotlinx.coroutines.delay
import org.aioncyclus.aiongraphos.R
import org.aioncyclus.aiongraphos.domain.model.zodiac.ZodiacPosition
import org.aioncyclus.aiongraphos.ui.mapper.iconOf
import org.aioncyclus.aiongraphos.ui.components.dignitychart.PlanetDignitySample
import org.aioncyclus.aiongraphos.ui.theme.ElementColour
import org.aioncyclus.aiongraphos.ui.theme.JetBrainsMono
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

@Composable
fun DignityChart(
    timeline: List<PlanetDignitySample>,
    modifier: Modifier = Modifier
) {
    val surfaceColour= MaterialTheme.colorScheme.surface
    val labelColour= MaterialTheme.colorScheme.onBackground
    val lineColour= MaterialTheme.colorScheme.primary

    val scrollState = rememberScrollState()
    Card(
        shape = RoundedCornerShape(10.dp),
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        )
        {

            val columnHeight=this.maxHeight
            val columnWidth=columnHeight * 0.24F
            val chartWidth = columnWidth * timeline.size
            val fontSize= (columnHeight * 0.040F).value
            val strokeThickness= (columnHeight * 0.015F).value


            Box(
                Modifier
                    .horizontalScroll(scrollState)
            )
            {
                Row(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    timeline.forEachIndexed { index, sample ->
                        DignityColumn(
                            sample = sample,
                            isTransparent = index % 2 == 0,
                            labelColour= labelColour,
                            modifier = Modifier
                                .width(columnWidth)
                                .height(columnHeight)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .width(chartWidth)
                        .fillMaxHeight(0.4f)
                        .align(Alignment.Center)
                ) {
                    LineChart(
                        values = timeline.map { it.score },
                        lineColour = lineColour,
                        labelColour=labelColour,
                        padding = columnWidth/2,
                        strokeThickness= strokeThickness,
                        textSize= fontSize,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

        }
    }
}

@Composable
fun DignityColumn(
    sample: PlanetDignitySample,
    isTransparent: Boolean,
    labelColour: Color,
    modifier: Modifier = Modifier
) {
    val signIconRes=iconOf(sample.zodiacPosition.sign)
    val signColour= ElementColour.of(sample.zodiacPosition.sign)
    var backgroundColour=signColour.copy(alpha = 0.05F)
    if(!isTransparent)
    {
        backgroundColour=signColour.copy(alpha = 0.15F)
    }
    Column(modifier
        .background(backgroundColour)) {

        DignityColumnHeader(
            instant = sample.instant,
            zoneId = sample.zoneId,
            zodiacPosition = sample.zodiacPosition,
            iconRes = signIconRes,
            signColour = signColour,
            labelColour =labelColour,
            modifier = Modifier.weight(2f)
        )

        DignityColumnSpacer(
            modifier = Modifier.weight(4f)
        )

        DignityColumnFooter(
            strengthPercentage = sample.strength,
            modifier = Modifier.weight(1f),
            labelColour = labelColour
        )
    }
}

@Composable
fun DignityColumnFooter(
    strengthPercentage: Double,
    modifier: Modifier = Modifier,
    labelColour: Color
)
{
    val textMeasurer = rememberTextMeasurer()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawFooterLabel(
                strength = strengthPercentage,
                textMeasurer = textMeasurer,
                labelColour=labelColour
            )
        }

    }

}

private fun DrawScope.drawFooterLabel(
    strength: Double,
    textMeasurer: TextMeasurer,
    labelColour: Color
)
{

    val fontSize = with(this) {
        (size.minDimension * 0.30f).toSp()
    }


    val textLayout = textMeasurer.measure(
        text = String.format("%.0f", strength)+"%",
        style = TextStyle(
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            color = labelColour,
            textAlign = TextAlign.Center
        )
    )

    drawText(
        textLayoutResult = textLayout,
        topLeft = Offset(
            x = (size.width - textLayout.size.width) / 2f,
            y = (size.height - textLayout.size.height)/ 2f
        )
    )

}




@Composable
fun DignityColumnHeader(
    instant: Instant,
    zoneId: ZoneId,
    zodiacPosition: ZodiacPosition,
    iconRes: Int,
    signColour: Color,
    labelColour: Color,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawHeaderLabels(
                textMeasurer = textMeasurer,
                instant = instant,
                zoneId = zoneId,
                degree = zodiacPosition.degreeInSign,
                minute= zodiacPosition.minuteInDegree,
                colour = labelColour
            )
        }

        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = signColour,
            modifier = Modifier
                .fillMaxWidth(0.50f)
                .aspectRatio(1f)
                .align(Alignment.Center)
        )
    }
}

private fun DrawScope.drawHeaderLabels(
    textMeasurer: TextMeasurer,
    instant: Instant,
    zoneId: ZoneId,
    degree: Int,
    minute: Int,
    colour: Color
)
{

    val dayY = size.height * 0.12f
    val dateY = size.height * 0.27f
    val degreeY = size.height * 0.75f

    val dayFontSize = with(this) {
        (size.minDimension * 0.18f).toSp()
    }
    val degreeFontSize=with(this) {
        (size.minDimension * 0.15f).toSp()
    }

    val dateTime = instant.atZone(zoneId)

    val dayText = dateTime.dayOfWeek.getDisplayName(
        java.time.format.TextStyle.SHORT,
        Locale.getDefault()
    ).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    val dateText = "${dateTime.dayOfMonth}/${dateTime.monthValue}"

    val degreeText= "${degree}°${minute.toString().padStart(2, '0')}'"

    val dayLayout = textMeasurer.measure(
        text = dayText,
        style = TextStyle(
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Medium,
            fontSize = dayFontSize,
            color = colour,
            textAlign = TextAlign.Center
        )
    )

    val dateLayout = textMeasurer.measure(
        text = dateText,
        style = TextStyle(
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = dayFontSize,
            color = colour,
            textAlign = TextAlign.Center
        )
    )

    val degreeLayout = textMeasurer.measure(
        text = degreeText,
        style = TextStyle(
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Bold,
            fontSize = degreeFontSize,
            color = colour,
            textAlign = TextAlign.Center
        )
    )

    drawText(
        textLayoutResult = dayLayout,
        topLeft = Offset(
            x = (size.width - dayLayout.size.width) / 2f,
            y = dayY- dayLayout.size.height / 2f
        )
    )
    drawText(
        textLayoutResult = dateLayout,
        topLeft = Offset(
            x = (size.width - dateLayout.size.width) / 2f,
            y = dateY - dateLayout.size.height / 2f
        )
    )
    drawText(
        textLayoutResult = degreeLayout,
        topLeft = Offset(
            x = (size.width - degreeLayout.size.width) / 2f,
            y = degreeY- degreeLayout.size.height / 2f
        )
    )
}

@Composable
fun DignityColumnSpacer(
    modifier: Modifier = Modifier
) {
    Box(modifier)
}



@Composable
fun LineChart(
    values: List<Int>,
    lineColour:Color,
    labelColour:Color,
    padding: Dp,
    textSize: Float,
    strokeThickness: Float,
    modifier: Modifier = Modifier
) {

    val splitYKey = remember {
        ExtraStore.Key<Float>()
    }

    val modelProducer = remember { CartesianChartModelProducer() }

    val typeface = ResourcesCompat.getFont(
        LocalContext.current,
        R.font.jetbrainsmono_medium
    )

    val textComponent = remember {
        TextComponent(
            color = labelColour.toArgb(),
            typeface = typeface,
            textSizeSp = textSize,
        )
    }
    LaunchedEffect(values) {
        val min = values.minOrNull() ?: 0
        val max = values.maxOrNull() ?: 0

        val padding = (max - min) * 0.9f

        val baseline = min - padding

        modelProducer.runTransaction {
            val xValues = values.indices.map { index ->
                index
            }
            lineSeries {
                series(
                    xValues,
                    values.map { it.toFloat() }
                )
            }

            extras {
                it[splitYKey] = baseline
            }
        }
    }
    val fillColours = intArrayOf(
        lineColour.copy(alpha = 0.50f).toArgb(),
        Color.Transparent.toArgb()
    )

    val lineProvider = LineCartesianLayer.LineProvider.series(
        LineCartesianLayer.Line(
            fill = LineCartesianLayer.LineFill.single(
                Fill(lineColour.toArgb())
            ),
            stroke = LineCartesianLayer.LineStroke.Continuous(
                thicknessDp = strokeThickness,
                cap =  android.graphics.Paint.Cap.ROUND
            ),
            pointConnector = LineCartesianLayer.PointConnector.cubic(),
            areaFill = LineCartesianLayer.AreaFill.single(
                Fill(ShaderProvider.verticalGradient(fillColours)),
                splitY = { extraStore ->
                    extraStore[splitYKey]
                }
            ),
            dataLabel = textComponent,
            dataLabelValueFormatter = { _, value, _ ->  String.format("%.0f", value) },
        )
    )
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(lineProvider = lineProvider),
            startAxis = null,
            bottomAxis = null,
            getXStep = { 1.0 },
            layerPadding = {
                cartesianLayerPadding(
                    scalableStart = padding/3,
                    scalableEnd = padding/3,
                )
            },
        ),
        modelProducer = modelProducer,
        scrollState = rememberVicoScrollState(scrollEnabled = false),
        zoomState = rememberVicoZoomState(zoomEnabled = false),
        animateIn = false,
        modifier = modifier
    )
}