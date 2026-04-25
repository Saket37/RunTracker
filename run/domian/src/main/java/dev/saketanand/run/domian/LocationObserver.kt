package dev.saketanand.run.domian

import dev.saketanand.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {

    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
}