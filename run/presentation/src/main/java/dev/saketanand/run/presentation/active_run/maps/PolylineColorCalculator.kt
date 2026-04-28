package dev.saketanand.run.presentation.active_run.maps

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import dev.saketanand.core.domain.location.LocationWithTimestamp
import kotlin.math.abs

object PolylineColorCalculator {

    fun locationsToColor(
        location1: LocationWithTimestamp,
        location2: LocationWithTimestamp
    ): Color {
        val distanceMeters = location1.location.location.distanceTo(location2.location.location)
        val timeDiff =
            abs((location2.durationTimestamp - location1.durationTimestamp).inWholeSeconds)
        val speedKmh = (distanceMeters / timeDiff) * 3.6

        return interpolateColor(
            speedKmh = speedKmh,
            minSpeedKmh = 5.0,
            maxSpeedKmh = 20.0,
            colorStart = Color.Green,
            colorMid = Color.Yellow,
            colorEnd = Color.Red
        )
    }

    private fun interpolateColor(
        speedKmh: Double,
        minSpeedKmh: Double,
        maxSpeedKmh: Double,
        colorStart: Color,
        colorMid: Color,
        colorEnd: Color
    ): Color {
        val ratio = ((speedKmh - minSpeedKmh) / (maxSpeedKmh - minSpeedKmh)).coerceIn(0.0..1.0)
        val colorInt = if (ratio <= 0.5) {
            val midRation = ratio / 0.5
            ColorUtils.blendARGB(colorStart.toArgb(), colorMid.toArgb(), midRation.toFloat())
        } else {
            val midToEndRatio = (ratio - 0.5) / 0.5
            ColorUtils.blendARGB(colorMid.toArgb(), colorEnd.toArgb(), midToEndRatio.toFloat())
        }
        return Color(colorInt)
    }
}
