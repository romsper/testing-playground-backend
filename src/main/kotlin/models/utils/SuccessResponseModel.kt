package models.utils

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponseModel(
    val code: Int = HttpStatusCode.OK.value,
    val message: String,
)
