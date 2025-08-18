package services

import enums.OrderStatusEnum
import models.order.OrderModel
import models.order.OrderRequestModel
import models.product.ProductModel
import repositories.OrderRepository
import repositories.ProductRepository
import repositories.UserRepository

class OrderService(private val orderRepository: OrderRepository, private val productRepository: ProductRepository, private val userRepository: UserRepository) {

    suspend fun getAll(offset: Long, limit: Int): List<OrderModel> = orderRepository.getAllOrders(offset, limit)

    suspend fun getAllByUserEmail(email: String, offset: Long, limit: Int): List<OrderModel>? {
        val user = userRepository.findUserByEmail(email) ?: return null
        return orderRepository.getAllOrdersByUserId(user.id, offset, limit)
    }

    suspend fun findOrderById(id: Int): OrderModel? = orderRepository.findOrderById(id)

    suspend fun findOrderByUserId(userId: Int): List<OrderModel> = orderRepository.findOrderByUserId(userId)

    suspend fun createOrder(request: OrderRequestModel): OrderModel? {
        val productList = mutableListOf<ProductModel>()
        request.products.forEach {
            productRepository.findProductById(it.id)?.let { product -> productList.add(product) } ?: return null
        }

        return orderRepository.createOrder(request.userId, productList)
    }

    suspend fun updateOrderStatus(orderId: Int, status: String): OrderModel? {
        return OrderStatusEnum.entries.find { it.status == status }
            ?.let { orderRepository.updateOrderStatus(orderId, OrderStatusEnum.valueOf(status)) }
    }
}