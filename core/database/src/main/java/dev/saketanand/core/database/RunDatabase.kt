package dev.saketanand.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.saketanand.core.database.dao.RunDao
import dev.saketanand.core.database.entity.RunEntity

@Database(
    entities = [RunEntity::class],
    version = 1
)
abstract class RunDatabase : RoomDatabase() {
    abstract val runDao: RunDao
}