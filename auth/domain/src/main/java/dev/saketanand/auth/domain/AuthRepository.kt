package dev.saketanand.auth.domain

import dev.saketanand.core.domain.DataError
import dev.saketanand.core.domain.EmptyResult

interface AuthRepository {

    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun register(email: String, password: String) : EmptyResult<DataError.Network>
}