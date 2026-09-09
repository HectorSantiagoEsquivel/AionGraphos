package org.aioncyclus.aiongraphos.ui.components.gauge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.aioncyclus.aiongraphos.domain.model.aspect.Distance
import org.aioncyclus.aiongraphos.ui.theme.JetBrainsMono
import kotlin.math.cos
import kotlin.math.sin

private const val START_ANGLE = 150f
private const val TOTAL_SWEEP = 240f
private const val STATIONARY_SWEEP = 12f
private const val GAP = 2f
private const val SLOW_SWEEP = 106f
private const val SWIFT_SWEEP = 118f


@Composable
fun ProgressGauge(
    value: Double,
    labelSize:Float =0.12F,
    labelVerticalPosition: Float=0.82F,
    trackColour: Color,
    progressColour: Color,
    labelColour: Color
)
{
    val textMeasurer = rememberTextMeasurer(0)
    Canvas(modifier = Modifier.fillMaxSize()) {
        val clampedValue = value.coerceIn(0.0, 100.0)
        val gaugeGeometry= gaugeGeometry(size)
        val strokeWidth = gaugeGeometry.strokeWidth
        val progressAngle = ((clampedValue / 100f) * TOTAL_SWEEP).toFloat()


        val arcRect = gaugeGeometry.arcRect
        val stroke=Stroke(width = strokeWidth, cap = StrokeCap.Round)

        drawGaugeSegment(trackColour.copy(alpha =  0.1f),
            START_ANGLE,
            TOTAL_SWEEP,
            arcRect,
            stroke)
        drawGaugeSegment(progressColour,
            START_ANGLE,
            progressAngle,
            arcRect,
            stroke)
        drawStrengthLabel(gaugeGeometry,
            labelSize,
            labelVerticalPosition,
            textMeasurer,
            value,
            labelColour)

    }
}


private fun DrawScope.drawStrengthLabel(
    gaugeGeometry: GaugeGeometry,
    labelSize: Float,
    labelVerticalPosition:Float,
    textMeasurer: TextMeasurer,
    strength: Double,
    color: Color
)
{
    val valueFont = gaugeGeometry.radius * labelSize
    val valueText = String.format("%.0f", strength)+"%"
    val valueLayout = textMeasurer.measure(
        text = valueText,
        style = TextStyle(
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = valueFont.sp,
            color = color,
            textAlign = TextAlign.Center
        )
    )
    drawText(
        textLayoutResult = valueLayout,
        topLeft = Offset(
            x = gaugeGeometry.center.x - valueLayout.size.width / 2f,
            gaugeGeometry.center.y +
                    gaugeGeometry.radius * labelVerticalPosition -
                    valueLayout.size.height / 2f
        )
    )
}

@Composable
fun NeedleGauge(
    speed: Distance,
    gaugeValue: Double,
    labelColour: Color,
    stationaryColour: Color,
    slowColour: Color,
    swiftColour: Color
) {
    val textMeasurer = rememberTextMeasurer(0)
    Canvas(modifier = Modifier.fillMaxSize()) {


        val clampedValue = gaugeValue.coerceIn(0.0, 100.0)
        val gaugeGeometry= gaugeGeometry(size)

        drawSpeedArc(gaugeGeometry,
            stationaryColour,
            slowColour,
            swiftColour)
        // Mean marker
        drawAngleMarker(gaugeGeometry,labelColour)
        drawSpeedLabels(gaugeGeometry,textMeasurer,speed,gaugeValue,labelColour)
        drawNeedle(gaugeGeometry,clampedValue,labelColour)

    }
}



private fun DrawScope.drawSpeedLabels(
    gaugeGeometry: GaugeGeometry,
    textMeasurer: TextMeasurer,
    speed: Distance,
    gaugeValue: Double,
    color: Color
)
{
    val titleFont = gaugeGeometry.radius * 0.11f
    val valueFont = gaugeGeometry.radius * 0.12f
    val labelFont = gaugeGeometry.radius * 0.10f
    // 1. "Speed" label (Top-Left)
    val titleText = "Speed"
    drawText(
        textMeasurer = textMeasurer,
        text = titleText,
        style = TextStyle(
            fontSize = titleFont.sp,
            color = color
        ),
        topLeft = Offset(
            8.dp.toPx(),
            8.dp.toPx()
        )
    )

    // 2. Speed value (Bottom-Center)
    val valueText = "${speed.degree}°${speed.minute.toString().padStart(2, '0')}'"
    val valueLayout = textMeasurer.measure(
        text = valueText,
        style = TextStyle(
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Normal,
            fontSize = valueFont.sp,
            color = color,
            textAlign = TextAlign.Center
        )
    )
    drawText(
        textLayoutResult = valueLayout,
        topLeft = Offset(
            x = gaugeGeometry.center.x - valueLayout.size.width / 2.5f,
            y = gaugeGeometry.center.y + gaugeGeometry.radius - valueLayout.size.height - 8.dp.toPx()
        )
    )

    // 3. Status label (Bottom-Right)
    val statusText = when {
        speed.absolute < 1.0 / 3600.0 -> "Stationary"
        gaugeValue < 50 -> "Slow"
        gaugeValue > 50 -> "Swift"
        else -> ""
    }
    val statusLayout = textMeasurer.measure(
        text = statusText,
        style = TextStyle(
            fontSize = labelFont.sp,
            color = color,
            textAlign = TextAlign.Center
        )
    )
    drawText(
        textLayoutResult = statusLayout,
        topLeft = Offset(
            size.width - statusLayout.size.width - 8.dp.toPx(),
            size.height - statusLayout.size.height - 8.dp.toPx()
        )
    )
}

private fun DrawScope.drawSpeedArc(
    gaugeGeometry: GaugeGeometry,
    stationaryColour: Color,
    slowColour: Color,
    swiftColour: Color
)
{

    val arcRect =gaugeGeometry.arcRect

    val endStroke = Stroke(
        width = gaugeGeometry.strokeWidth,
        cap = StrokeCap.Round
    )
    val middleStroke = Stroke(
        width = gaugeGeometry.strokeWidth,
        cap = StrokeCap.Butt
    )

    // Stationary
    drawGaugeSegment(stationaryColour,START_ANGLE,1F,arcRect,endStroke)
    drawGaugeSegment(stationaryColour,START_ANGLE,STATIONARY_SWEEP,arcRect,middleStroke)
    // Slow
    drawGaugeSegment(slowColour,
        START_ANGLE+STATIONARY_SWEEP+GAP,
        SLOW_SWEEP-GAP,
        arcRect,
        middleStroke)
    // Swift
    drawGaugeSegment(swiftColour,
        START_ANGLE + STATIONARY_SWEEP + GAP + SLOW_SWEEP+GAP,
        SWIFT_SWEEP,
        arcRect,
        middleStroke)
    drawGaugeSegment(swiftColour,
        START_ANGLE + STATIONARY_SWEEP + GAP + SLOW_SWEEP + SWIFT_SWEEP,
        1F,
        arcRect,
        endStroke)
}

private fun DrawScope.drawGaugeSegment(
    color: Color,
    start: Float,
    sweep: Float,
    rect: Rect,
    style: Stroke
)
{
    drawArc(
        color = color,
        startAngle = start,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = rect.topLeft,
        size = rect.size,
        style = style
    )
}

private fun DrawScope.drawAngleMarker(
    geometry: GaugeGeometry,
    color: Color
)
{
    val angle = Math.toRadians((START_ANGLE + TOTAL_SWEEP / 2f).toDouble())
    val inner = geometry.radius - geometry.strokeWidth / 1.5f
    val outer = geometry.radius + geometry.strokeWidth / 1.5f
    drawLine(
        color = color,
        start = Offset(
            geometry.center.x + inner * cos(angle).toFloat(),
            geometry.center.y + inner * sin(angle).toFloat()
        ),
        end = Offset(
            geometry.center.x + outer * cos(angle).toFloat(),
            geometry.center.y + outer * sin(angle).toFloat()
        ),
        strokeWidth = geometry.baseWidth,
        cap = StrokeCap.Round
    )
}



private fun DrawScope.drawNeedle(
    gaugeGeometry: GaugeGeometry,
    gaugeValue: Double,
    color: Color
) {

    val center = gaugeGeometry.center
    val angleDegrees = (START_ANGLE + (gaugeValue / 100f) * TOTAL_SWEEP).toFloat()
    val radius = gaugeGeometry.radius
    val baseWidth = gaugeGeometry.baseWidth

    val angleRad = Math.toRadians(angleDegrees.toDouble())
    val needleLength = radius * 0.65f
    val ringRadius = baseWidth * 2f


    val startX = center.x + ringRadius * cos(angleRad).toFloat()
    val startY = center.y + ringRadius * sin(angleRad).toFloat()


    val tipX = center.x + needleLength * cos(angleRad).toFloat()
    val tipY = center.y + needleLength * sin(angleRad).toFloat()

    drawCircle(
        color = color,
        radius = ringRadius,
        center = Offset(center.x, center.y),
        style = Stroke(width = baseWidth)
    )

    drawLine(
        color = color,
        start = Offset(startX, startY),
        end = Offset(tipX, tipY),
        strokeWidth = baseWidth,
        cap = StrokeCap.Round
    )
}

