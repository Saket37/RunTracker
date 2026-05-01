package dev.saketanand.core.domain.run

import dev.saketanand.core.domain.location.Location
import java.time.ZonedDateTime
import kotlin.time.Duration
import kotlin.time.DurationUnit

data class Run(
    val id: String?,
    val duration: Duration,
    val dateTimeUtc: ZonedDateTime,
    val distanceInMeters: Double,
    val location: Location,
    val maxSpeedKmh: Double,
    val totalElevationMeters: Int,
    val mapPictureUrl: String?
) {
    val avgSpeedKmh: Double
        get() = (distanceInMeters / 1000.0) / duration.toDouble(DurationUnit.HOURS)
}
