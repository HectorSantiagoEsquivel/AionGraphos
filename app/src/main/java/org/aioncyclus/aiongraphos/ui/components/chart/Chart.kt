package org.aioncyclus.aiongraphos.ui.components.chart

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import org.aioncyclus.aiongraphos.R
import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.house.HousesData
import org.aioncyclus.aiongraphos.domain.model.lot.LotData
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign
import org.aioncyclus.aiongraphos.ui.mapper.iconOf
import org.aioncyclus.aiongraphos.ui.theme.AspectColour
import org.aioncyclus.aiongraphos.ui.theme.ElementColour
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Chart(chartUIState: ChartUIState,
    modifier: Modifier = Modifier)
{
    val backgroundColour=MaterialTheme.colorScheme.background
    val onBackgroundColour=MaterialTheme.colorScheme.onBackground
    val context = LocalContext.current
    val ringWidth = 75.0f
    Box(modifier = modifier
        .fillMaxWidth())
    {
        Box(
            modifier = modifier
                .aspectRatio(1f)
                .align(Alignment.Center)
                .padding(10.dp)
                .drawWithCache() {
                    val planetIconSize = size.width / 30f
                    val outerRadius = size.width / 2 - ringWidth / 2
                    val innerRadius = outerRadius -(ringWidth/1.8F)
                    val planetRadius = innerRadius-planetIconSize
                    val coreRadius = innerRadius/2F
                    onDrawBehind {


                        drawZodiacWheel(
                            outerRadius=outerRadius,
                            innerRadius=innerRadius,
                            coreRadius=coreRadius,
                            backgroundColour = backgroundColour,
                            ringWidth=ringWidth,
                            context=context)
                        drawHouses(
                            housesData = chartUIState.housesData,
                            colour = backgroundColour,
                            innerRadius=coreRadius,
                            outerRadius=innerRadius,
                            context = context)
                        val occupiedPositions=
                            drawPlanets(
                            planetaryData =chartUIState.planetaryData,
                            outerRadius=planetRadius,
                            iconSize = planetIconSize,
                            colour = onBackgroundColour,
                            context=context)
                        drawAspects(
                            planetaryData = chartUIState.planetaryData,
                            outerRadius=coreRadius,
                            degreeIndicatorColour = onBackgroundColour,
                            degreeIndicatorLength = size.width/120F,
                            aspects = chartUIState.aspects
                        )
                        drawPlanets(
                            planetaryData = chartUIState.nodeData,
                            outerRadius=planetRadius,
                            iconSize = planetIconSize,
                            colour = onBackgroundColour,
                            context=context,
                            occupiedPositions = occupiedPositions)
                        drawLots(
                            lots = chartUIState.lotData,
                            outerRadius=planetRadius,
                            iconSize = planetIconSize,
                            colour = onBackgroundColour,
                            context=context,
                            occupiedPositions = occupiedPositions
                        )
                    }
                }
        )
    }
}

private fun DrawScope.drawAspects(
    planetaryData: List<PlanetData>,
    aspects: List<Aspect>,
    degreeIndicatorColour: Color,
    degreeIndicatorLength: Float,
    outerRadius: Float,
    aspectThickness: Float=size.width / 250f,
) {

    val positions = planetaryData.associate {
        it.planet to it.planetPosition.longitude
    }

    for (aspect in aspects) {
        val normalizedDistance =
            (aspect.separation.absolute / aspect.aspectType.defaultOrb).coerceIn(0.0, 1.0)

        val planetALongitude = positions[aspect.planetA] ?: continue
        val planetBLongitude = positions[aspect.planetB] ?: continue

        val planetAAngle = Math.toRadians(planetToCanvasAngle(planetALongitude))
        val planetBAngle = Math.toRadians(planetToCanvasAngle(planetBLongitude))

        val planetAPosition = drawAngleLine(
            angle = planetAAngle,
            innerRadius = outerRadius - degreeIndicatorLength,
            outerRadius = outerRadius,
            colour = degreeIndicatorColour
        )

        val planetBPosition = drawAngleLine(
            angle = planetBAngle,
            innerRadius = outerRadius - degreeIndicatorLength,
            outerRadius = outerRadius,
            colour = degreeIndicatorColour
        )

        drawLine(
            color = AspectColour.of(aspect),
            start = planetAPosition,
            end = planetBPosition,
            strokeWidth = (aspectThickness * (1.0 - normalizedDistance * normalizedDistance)).toFloat()
        )
    }
}

private fun DrawScope.drawLots(
    lots: List<LotData>,
    colour: Color,
    outerRadius: Float,
    iconSize: Float,
    context: Context,
    occupiedPositions: MutableList<Offset> = mutableListOf()
): MutableList<Offset>
{
    for(lot in lots)
    {
        val angle =Math.toRadians(
            planetToCanvasAngle(lot.longitude)
        )
        val iconRes=iconOf(lot.lotType)
        drawAngleLine(
            angle = angle,
            innerRadius = outerRadius + iconSize / 1.5f,
            outerRadius = outerRadius + iconSize,
            colour = colour
        )
        occupiedPositions+=drawAstroObject(
            iconRes = iconRes,
            angle=angle,
            outerRadius=outerRadius,
            iconSize=iconSize,
            occupiedPositions=occupiedPositions,
            colour=colour,
            context=context)
    }
    return occupiedPositions
}

private fun DrawScope.drawPlanets(
    planetaryData: List<PlanetData>,
    colour: Color,
    outerRadius: Float,
    iconSize: Float,
    context: Context,
    occupiedPositions:MutableList<Offset>  = mutableListOf<Offset>()
):MutableList<Offset> {

    for (planetData in planetaryData) {
        val angle = Math.toRadians(
            planetToCanvasAngle(planetData.planetPosition.longitude)
        )
        val iconRes= iconOf(planetData.planet)
        drawAngleLine(
            angle = angle,
            innerRadius = outerRadius + iconSize / 1.5f,
            outerRadius = outerRadius + iconSize,
            colour = colour
        )
        occupiedPositions+=drawAstroObject(
            iconRes = iconRes,
            angle=angle,
            outerRadius=outerRadius,
            iconSize=iconSize,
            occupiedPositions=occupiedPositions,
            colour=colour,
            context=context)
    }
    return occupiedPositions
}
private fun DrawScope.drawAngleLine(
    angle: Double,
    innerRadius: Float,
    outerRadius: Float,
    colour: Color,
    strokeWidth: Float = size.width / 250f
): Offset {
    val lineStart= Offset(
        x = center.x + innerRadius * cos(angle).toFloat(),
        y = center.y + innerRadius * sin(angle).toFloat()
    )
    val lineEnd= Offset(
        x = center.x + outerRadius * cos(angle).toFloat(),
        y = center.y + outerRadius * sin(angle).toFloat()
    )

    drawLine(
        color = colour,
        start = lineStart,
        end = lineEnd,
        strokeWidth = strokeWidth
    )
    return lineStart
}

private fun DrawScope.drawAstroObject(
    iconRes: Int,
    angle: Double,
    outerRadius: Float,
    iconSize: Float,
    occupiedPositions: List<Offset>,
    colour: Color,
    context: Context
): Offset
{
    val position = findPlanetPosition(angle,outerRadius,iconSize,occupiedPositions)

    val drawable = ContextCompat.getDrawable(context, iconRes)

    drawIntoCanvas { canvas ->
        canvas.save()
        drawable.colorFilter = PorterDuffColorFilter(
            colour.toArgb(),
            PorterDuff.Mode.SRC_IN
        )
        drawable.setBounds(
            (position.x - iconSize).toInt(),
            (position.y - iconSize).toInt(),
            (position.x + iconSize).toInt(),
            (position.y + iconSize).toInt()
        )

        drawable.draw(canvas.nativeCanvas)

        canvas.restore()
    }
    return position
}
//TODO=Swap findPlanetPosition's spiral collision avoiding algorithm with one that more accurately reflects planet position
private fun DrawScope.findPlanetPosition(
    angle: Double,
    outerRadius: Float,
    iconSize: Float,
    occupiedPositions: List<Offset>
): Offset {

    val radiusStep = iconSize * 1.5f

    fun positionAt(radius: Float, angle: Double): Offset =
        Offset(
            center.x + radius * cos(angle).toFloat(),
            center.y + radius * sin(angle).toFloat()
        )

    fun isFree(position: Offset): Boolean {
        val minDistance = iconSize*1.5F
        val minDistanceSquared = minDistance * minDistance

        return occupiedPositions.none {
            val delta = position - it
            delta.getDistanceSquared() < minDistanceSquared
        }
    }

    for (level in 0 until 10) {

        val radius = outerRadius - level * radiusStep

        if (radius <= iconSize) break

        val angularStep = iconSize*0.5 / radius

        for (direction in listOf(0,1,-1)) {
            val candidateAngle = angle + direction * angularStep
            val candidate = positionAt(radius, candidateAngle)

            if (isFree(candidate)) {
                return candidate
            }
        }
    }
    return positionAt(outerRadius, angle)
}
private fun DrawScope.drawHouses(
    housesData: HousesData,
    colour: Color,
    innerRadius: Float,
    outerRadius: Float,
    context: Context
) {

    for (i in housesData.houses.indices) {
        val currentCusp = housesData.houses[i].cusp
        val nextCusp = housesData.houses[(i + 1) % housesData.houses.size].cusp
        val midpoint = midpoint(zodiacToCanvasAngle(currentCusp),zodiacToCanvasAngle(nextCusp))

        drawHouseLine(
            zodiacToCanvasAngle(currentCusp),
            colour,
            innerRadius,
            outerRadius)
        drawHouseLabel(
            number = i + 1,
            angleInDegrees = midpoint,
            radius = innerRadius*1.5F,
            colour = colour,
            context = context
        )
    }

}

fun midpoint(a: Double, b: Double): Double {
    var difference = (b - a + 360f) % 360f

    return (a + difference / 2f) % 360f
}

private fun DrawScope.drawHouseLabel(
    number: Int,
    angleInDegrees: Double,
    radius: Float,
    colour: Color,
    context: Context
) {
    val anton = ResourcesCompat.getFont(
        context,
        R.font.anton_regular

    )

    val angle = Math.toRadians(angleInDegrees)

    val position = Offset(
        x = center.x + radius * cos(angle).toFloat(),
        y = center.y + radius * sin(angle).toFloat()
    )

    val paint = android.graphics.Paint().apply {
        color = colour.toArgb()
        textSize = size.width / 18f
        textAlign = android.graphics.Paint.Align.CENTER
        isAntiAlias = true
        typeface=anton
    }

    drawIntoCanvas { canvas ->
        canvas.nativeCanvas.save()
        canvas.nativeCanvas.rotate(
            (angleInDegrees + 90f).toFloat(),
            position.x,
            position.y
        )
        canvas.nativeCanvas.drawText(
            toRomanNumeral(number),
            position.x,
            position.y - (paint.ascent() + paint.descent()) / 2,
            paint
        )
        canvas.nativeCanvas.restore()

    }
}
private fun DrawScope.drawHouseLine(
    angleInDegrees: Double,
    colour: Color,
    innerRadius: Float,
    outerRadius: Float
) {
    val houseLineWidth = size.width / 200f
    val angle = Math.toRadians(angleInDegrees).toFloat()

    val start = Offset(
        center.x + innerRadius * cos(angle),
        center.y + innerRadius * sin(angle)
    )

    val end = Offset(
        center.x + outerRadius * cos(angle),
        center.y + outerRadius * sin(angle)
    )

    drawLine(
        color = colour,
        start = start,
        end = end,
        strokeWidth = houseLineWidth
    )
}
private fun DrawScope.drawZodiacWheel(
    outerRadius: Float,
    innerRadius: Float,
    coreRadius: Float,
    backgroundColour: Color,
    ringWidth: Float,
    context: Context
)
{
    val arcAngle=360/(Sign.entries.size).toFloat()
    var currentAngle=0F
    val outerOffset = Offset(
        center.x - outerRadius,
        center.y - outerRadius
    )

    val innerOffset = Offset(
        center.x - innerRadius,
        center.y - innerRadius
    )
    for (sign in Sign.entries.asReversed()) {

        val drawable = ContextCompat.getDrawable(context, iconOf(sign = sign))

        val angle = Math.toRadians(
            (currentAngle + arcAngle / 2).toDouble()
        )

        val x = center.x + outerRadius * cos(angle).toFloat()
        val y = center.y + outerRadius * sin(angle).toFloat()
        drawArc(
            color = ElementColour.of(sign).copy(alpha = 0.15F),
            startAngle = currentAngle,
            sweepAngle = arcAngle,
            useCenter = true,
            topLeft = innerOffset,
            size = Size(innerRadius * 2, innerRadius * 2),
        )

        drawOuterRingSection(
            colour = ElementColour.of(sign),
            startAngle = currentAngle,
            sweepAngle = arcAngle,
            topLeft = outerOffset,
            radius=outerRadius,
            width = ringWidth,
            iconCenter = Offset(x,y),
            drawable = drawable
        )
        currentAngle+=arcAngle
    }
    drawCircle(
        color = backgroundColour,
        radius = coreRadius
    )
    drawDegrees(backgroundColour,innerRadius)
}
private fun DrawScope.drawOuterRingSection(
    colour: Color,
    startAngle: Float,
    sweepAngle: Float,
    topLeft: Offset,
    radius: Float,
    iconCenter: Offset,
    drawable: Drawable,
    width: Float
) {

    drawArc(
        color = colour.copy(alpha = 0.30F),
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = topLeft,
        size = Size(radius * 2, radius * 2),
        style = Stroke(width)
    )
    drawIntoCanvas { canvas ->
        canvas.save()
        drawable.colorFilter = PorterDuffColorFilter(
            colour.toArgb(),
            PorterDuff.Mode.SRC_IN
        )

        canvas.nativeCanvas.rotate(
            startAngle + sweepAngle / 2 + 90f,
            iconCenter.x,
            iconCenter.y
        )

        drawable.setBounds(
            (iconCenter.x - width / 3).toInt(),
            (iconCenter.y - width / 3).toInt(),
            (iconCenter.x + width / 3).toInt(),
            (iconCenter.y + width / 3).toInt()
        )

        drawable.draw(canvas.nativeCanvas)

        canvas.restore()
    }
}
private fun DrawScope.drawDegrees(
    colour: Color,
    radius: Float,
)
{
    val signDegreeLength =size.width/35F
    val decanDegreeLength =size.width/45F
    val normalDegreeLength =size.width/60F

    val signDegreeWidth=size.width/150f
    val decanDegreeWidth=size.width/250f
    val normalDegreeWidth=size.width/500f

    for (angle in 0 until 360) {
        val isSignTick = angle % 30 == 0
        val isDecanTick = angle % 10 ==0

        val tickLength = if (isSignTick) signDegreeLength else if (isDecanTick) decanDegreeLength else normalDegreeLength
        val tickWidth = if (isSignTick) signDegreeWidth else if (isDecanTick) decanDegreeWidth else normalDegreeWidth
        rotate(degrees = angle.toFloat(), pivot = center) {

            drawLine(
                color = colour,
                start = Offset(x = center.x, y = center.y - radius - tickLength),
                end = Offset(x = center.x, y = center.y - radius ),
                strokeWidth = tickWidth
            )
        }
    }
}

fun zodiacToCanvasAngle(angle: Double): Double =
    (180 - angle) % 360.0
fun planetToCanvasAngle(angle: Double): Double=
    (360-angle) %360
private fun toRomanNumeral(number:Int): String
{
    return when(number)
    {
        1 ->"I"
        2 ->"II"
        3->"III"
        4->"IV"
        5->"V"
        6->"VI"
        7->"VII"
        8->"VIII"
        9->"IX"
        10->"X"
        11->"XI"
        12->"XII"
        else ->""
    }

}

