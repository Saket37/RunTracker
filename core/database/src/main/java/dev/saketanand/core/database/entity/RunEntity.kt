package dev.saketanand.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity("runentity")
data class RunEntity(
    val durationMillis: Long,
    val distanceMeters: Int,
    val dateTimeUtc: String,
    val latitude: Double,
    val longitude: Double,
    val avgSpeedKmh: Double,
    val maxSpeedKmh: Double,
    val totalElevationMeters: Int,
    val mapPictureUrl: String?,
    @PrimaryKey(autoGenerate = true)
    val id: String = ObjectId().toHexString()
)