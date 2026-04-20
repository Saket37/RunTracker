package dev.saketanand.core.domain.location

import kotlin.time.Duration

data class LocationWithTimestamp(
    val location: LocationWithAltitude,
    val durationTimestamp: Duration
)
