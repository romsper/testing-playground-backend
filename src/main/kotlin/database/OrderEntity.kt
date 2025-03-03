package database

import enums.OrderStatusEnum
import kotlinx.serialization.json.Json
import models.order.OrderModel
import models.product.ProductModel
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.json.jsonb

object OrderEntity : IntIdTable("table_orders") {
    var UserId = reference("userId", UserEntity.id).nullable()
    var OrderStatus = enumeration("orderStatus", OrderStatusEnum::class).default(OrderStatusEnum.PENDING)
    var Products = jsonb<List<ProductModel>>("products", Json.Default)
    var TotalAmount = double("totalAmount")
    var CreatedAt = long("createdAt")
    var UpdatedAt = long("updatedAt")
}

fun OrderEntity.toModel(resultRow: ResultRow) = OrderModel(
    id = resultRow[id].value,
    userId = resultRow[UserId]?.value,
    orderStatus = resultRow[OrderStatus].status,
    products = resultRow[Products],
    totalAmount = resultRow[TotalAmount],
    createdAt = resultRow[CreatedAt],
    updatedAt = resultRow[UpdatedAt]
)