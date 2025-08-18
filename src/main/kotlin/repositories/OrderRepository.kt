package repositories

import enums.OrderStatusEnum
import database.OrderEntity
import database.toModel
import models.order.OrderModel
import models.product.ProductModel
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning
import org.joda.time.DateTime
import utils.executeQuery

class OrderRepository {

    suspend fun getAllOrders(offset: Long, limit: Int): List<OrderModel> {
        return executeQuery {
            OrderEntity.selectAll()
                .limit(limit.coerceAtMost(50))
                .offset(offset)
                .map { OrderEntity.toModel(it) }
        } ?: emptyList()
    }

    suspend fun getAllOrdersByUserId(id: Int, offset: Long, limit: Int): List<OrderModel> {
        return executeQuery {
            OrderEntity.selectAll()
                .where { OrderEntity.UserId.eq(id) }
                .limit(limit.coerceAtMost(50))
                .offset(offset)
                .map { OrderEntity.toModel(it) }
        } ?: emptyList()
    }

    suspend fun findOrderById(id: Int): OrderModel? {
        return executeQuery {
            OrderEntity.selectAll().where { OrderEntity.id.eq(id) }.firstOrNull()
                ?.let { OrderEntity.toModel(it) }
        }
    }

    suspend fun findOrderByUserId(userId: Int): List<OrderModel> {
        return executeQuery {
            OrderEntity.selectAll().where { OrderEntity.UserId.eq(userId) }
                .map { OrderEntity.toModel(it) }
        } ?: emptyList()
    }

    suspend fun createOrder(userId: Int?, productList: List<ProductModel>): OrderModel? {
        return executeQuery {
            OrderEntity.insertReturning {
                userId?.let { id -> it[UserId] = id }
                it[OrderStatus] = OrderStatusEnum.PENDING
                it[Products] = productList
                it[TotalAmount] = productList.sumOf { product -> product.price }
                it[CreatedAt] = DateTime.now().millis
                it[UpdatedAt] = DateTime.now().millis
            }.map { OrderEntity.toModel(it) }.firstOrNull()
        }
    }

    suspend fun updateOrderStatus(orderId: Int, status: OrderStatusEnum): OrderModel? {
        return executeQuery {
            OrderEntity.updateReturning(where = { OrderEntity.id.eq(orderId) }) {
                it[OrderStatus] = status
                it[UpdatedAt] = DateTime.now().millis
            }.map { OrderEntity.toModel(it) }.firstOrNull()
        }
    }
}