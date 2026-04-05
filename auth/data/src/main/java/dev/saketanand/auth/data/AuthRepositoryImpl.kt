package dev.saketanand.auth.data

import dev.saketanand.auth.domain.AuthRepository
import dev.saketanand.core.data.networking.post
import dev.saketanand.core.domain.DataError
import dev.saketanand.core.domain.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {
    override suspend fun register(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email, password = password
            )
        )
    }
}