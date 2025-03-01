package models.user

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val phoneNumber: String,
    val activated: Boolean,
    val createdAt: Long
)

@Serializable
data class UserResponseModel(
    val id: Int,
    val username: String,
    val email: String,
    val activationUUID: String?,
    val phoneNumber: String?,
    val activated: Boolean,
)

@Serializable
data class UserRequestModel(
    val username: String?,
    val password: String?,
    val email: String?,
)

@Serializable
data class UserUpdateModel(
    val username: String?,
    val password: String?,
    val email: String?,
    val phoneNumber: String?,
    val activated: Boolean?,
)

@Serializable
data class UserActivateModel(
    val username: String,
    val password: String,
    val phoneNumber: String?,
)
