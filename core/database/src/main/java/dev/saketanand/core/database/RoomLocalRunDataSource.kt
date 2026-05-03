package dev.saketanand.core.database

import android.database.sqlite.SQLiteFullException
import dev.saketanand.core.database.dao.RunDao
import dev.saketanand.core.database.mappers.toRun
import dev.saketanand.core.database.mappers.toRunEntity
import dev.saketanand.core.domain.DataError
import dev.saketanand.core.domain.Result
import dev.saketanand.core.domain.run.LocalRunDataSource
import dev.saketanand.core.domain.run.Run
import dev.saketanand.core.domain.run.RunId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalRunDataSource(
    private val runDao: RunDao
) : LocalRunDataSource {
    override fun getRuns(): Flow<List<Run>> {
        return runDao.getRuns().map { runEntities ->
            runEntities.map { it.toRun() }
        }
    }

    override suspend fun upsertRun(run: Run): Result<RunId, DataError.Local> {
        return try {
            val entity = run.toRunEntity()
            runDao.upsertRun(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertRuns(run: List<Run>): Result<List<RunId>, DataError.Local> {
        return try {
            val entities = run.map { it.toRunEntity() }
            runDao.upsertRuns(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRun(id: String) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteRuns() {
        runDao.deleteAllRuns()
    }
}