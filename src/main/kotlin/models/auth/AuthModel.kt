package models.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestModel(
    val email: String?,
    val password: String?
)

@Serializable
data class AuthResponseModel(
    val id: Int,
    val accessToken: String,
    val refreshToken: String,
    val createdAt: Long,
    val expireInMs: Long
)

@Serializable
data class AuthRefreshRequestModel(
    val refreshToken: String
)
