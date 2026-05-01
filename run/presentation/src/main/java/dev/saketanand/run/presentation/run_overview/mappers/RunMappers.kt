package dev.saketanand.run.presentation.run_overview.mappers

import dev.saketanand.core.domain.run.Run
import dev.saketanand.core.presentation.ui.formatted
import dev.saketanand.core.presentation.ui.toFormattedKmh
import dev.saketanand.core.presentation.ui.toFormattedKms
import dev.saketanand.core.presentation.ui.toFormattedMeters
import dev.saketanand.core.presentation.ui.toFormattedPace
import dev.saketanand.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Run.toRunUi(): RunUi {
    val dateTimeInLocalTime = dateTimeUtc.withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter.ofPattern("MMM dd, yyyy - hh:mma")
        .format(dateTimeInLocalTime)
    val distanceKm = distanceInMeters / 1000.0
    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTIme = formattedDateTime,
        distance = distanceKm.toFormattedKms(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )
}