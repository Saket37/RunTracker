package dev.saketanand.auth.data

import dev.saketanand.auth.domain.AuthRepository
import dev.saketanand.core.data.networking.post
import dev.saketanand.core.domain.AuthInfo
import dev.saketanand.core.domain.DataError
import dev.saketanand.core.domain.EmptyResult
import dev.saketanand.core.domain.Result
import dev.saketanand.core.domain.SessionStorage
import dev.saketanand.core.domain.asEmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
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

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()

    }
}