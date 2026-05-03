package dev.saketanand.core.domain.run

import dev.saketanand.core.domain.DataError
import dev.saketanand.core.domain.Result
import kotlinx.coroutines.flow.Flow

typealias RunId = String

interface LocalRunDataSource {

    fun getRuns(): Flow<List<Run>>

    suspend fun upsertRun(run: Run): Result<RunId, DataError.Local>

    suspend fun upsertRuns(run: List<Run>) : Result<List<RunId>, DataError.Local >

    suspend fun deleteRun(id: String)
    suspend fun deleteRuns()

}