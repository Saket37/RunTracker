package dev.saketanand.run.domian

import dev.saketanand.core.domain.location.LocationWithTimestamp
import kotlin.time.Duration

data class RunData(
    val distanceInMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val locations: List<List<LocationWithTimestamp>> = emptyList(),

    )
