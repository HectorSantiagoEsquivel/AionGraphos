package org.aioncyclus.aiongraphos.ui.components.gauge
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

data class GaugeGeometry(
    val center: Offset,
    val radius: Float,
    val strokeWidth: Float,
    val baseWidth: Float,
    val arcRect: Rect
)

fun gaugeGeometry(size: Size) : GaugeGeometry
{
    val radius = (size.minDimension / 2) * 0.72f
    val headerSpace = size.height * 0.08f
    val center = Offset(
        size.width / 2f,
        size.height / 2f + headerSpace
    )
    val strokeWidth = radius * 0.21f
    val baseWidth = radius * 0.045f
    val arcRect = Rect(
        Offset(center.x - radius, center.y - radius),
        Size(radius * 2, radius * 2)
    )

    return GaugeGeometry(center,radius,strokeWidth,baseWidth,arcRect)
}


