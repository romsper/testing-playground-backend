package controllers

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.request.*
import io.ktor.server.routing.*
import models.order.OrderModel
import models.order.OrderRequestModel
import models.order.OrderUpdateModel
import services.OrderService
import utils.badRequest
import utils.notFound
import utils.ok

fun Route.orderRoute(orderService: OrderService) {

    route("/orders") {

        authenticate("jwt-token") {

            get("/") {
                val offset = call.request.queryParameters["offset"]?.toLongOrNull() ?: 0
                val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 50

                when (val orders = orderService.getAll(offset, limit)) {
                    emptyList<OrderModel>() -> call.badRequest("Something went wrong. Please verify request.")
                    else -> call.ok(orders)
                }
            }

            get("/user") {
                val offset = call.request.queryParameters["offset"]?.toLongOrNull() ?: 0
                val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 50
                val principal = call.principal<JWTPrincipal>()
                when {
                    principal == null -> call.badRequest("JWT token data is missing or invalid")
                    else -> {
                        val email = principal.payload.getClaim("email").asString()
                        when (val orders = orderService.getAllByUserEmail(email, offset, limit)) {
                            null -> call.notFound("Orders for user with email:${email} not found")
                            else -> call.ok(orders)
                        }
                    }
                }
            }

            put("/{id}/status") {
                val orderId = call.parameters["id"]
                val status = call.receive<OrderUpdateModel>()

                when {
                    orderId.isNullOrBlank() -> call.badRequest("orderId is incorrect")
                    orderId.toIntOrNull() == null -> call.badRequest("orderId must be a number")
                    status.orderStatus.isNullOrBlank() -> call.badRequest("status cannot be null or empty")
                    else -> {
                        when (val order = orderService.updateOrderStatus(orderId.toInt(), status.orderStatus)) {
                            null -> call.badRequest("Something went wrong. Please verify request.")
                            else -> call.ok(order)
                        }
                    }
                }
            }
        }

        post("/create") {
            val request = call.receive<OrderRequestModel>()

            when {
                request.products.isNullOrEmpty() -> call.badRequest("products cannot be null or empty")
                else -> {
                    when (val order = orderService.createOrder(request)) {
                        null -> call.badRequest("Something went wrong. Please verify request.")
                        else -> call.ok(order)
                    }
                }
            }
        }

        get("/{id}") {
            val orderId = call.parameters["id"]
            when {
                orderId.isNullOrBlank() -> call.badRequest("orderId is incorrect")
                orderId.toIntOrNull() == null -> call.badRequest("orderId must be a number")
                else -> {
                    when (val order = orderService.findOrderById(orderId.toInt())) {
                        null -> call.badRequest("Order with id:${orderId} not found")
                        else -> call.ok(order)
                    }
                }
            }
        }

        get("/user/{id}") {
            val userId = call.parameters["id"]
            when {
                userId.isNullOrBlank() -> call.badRequest("userId is incorrect")
                userId.toIntOrNull() == null -> call.badRequest("userId must be a number")
                else -> {
                    when (val orders = orderService.findOrderByUserId(userId.toInt())) {
                        emptyList<OrderModel>() -> call.badRequest("Orders for user with id:${userId} not found")
                        else -> call.ok(orders)
                    }
                }
            }
        }
    }

}
