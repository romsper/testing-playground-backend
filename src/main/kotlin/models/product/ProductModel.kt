package models.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductModel(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
)

@Serializable
data class ProductOrderModel(
    val id: Int,
)

@Serializable
data class ProductRequestModel(
    val name: String,
    val price: Double,
    val description: String,
)

@Serializable
data class ProductUpdateModel(
    val id: Int?,
    val name: String?,
    val price: Double?,
    val description: String?,
)