package dev.saketanand.run.location

import android.location.Location
import dev.saketanand.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = dev.saketanand.core.domain.location.Location(lat = latitude, long = longitude),
        altitude = altitude
    )
}