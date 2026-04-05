package dev.saketanand.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    //val accessTokenTimestamp: String?,
    val userId: String
)
