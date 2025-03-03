package models.user

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val phoneNumber: String,
    val createdAt: Long
) {
    fun toShortModel() = UserResponseModel(
        id = id,
        username = username,
        email = email,
        phoneNumber = phoneNumber,
        createdAt = createdAt
    )
}

@Serializable
data class UserResponseModel(
    val id: Int,
    val username: String,
    val email: String,
    val phoneNumber: String?,
    val createdAt: Long
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
)
