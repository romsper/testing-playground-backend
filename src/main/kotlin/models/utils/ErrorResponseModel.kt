package models.utils

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseModel(
    val code: Int,
    val reason: String,
)
