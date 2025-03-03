package models.order

import kotlinx.serialization.Serializable
import models.product.ProductModel
import models.product.ProductOrderModel

@Serializable
data class OrderModel(
    val id: Int,
    val userId: Int?,
    val orderStatus: String,
    val products: List<ProductModel>,
    val totalAmount: Double,
    val createdAt: Long,
    val updatedAt: Long
)

@Serializable
data class OrderRequestModel(
    val userId: Int?,
    val products: List<ProductOrderModel>,
)

@Serializable
data class OrderUpdateModel(
    val orderStatus: String,
)