package controllers

import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import models.product.ProductModel
import models.product.ProductRequestModel
import models.product.ProductUpdateModel
import services.ProductService
import utils.badRequest
import utils.ok

fun Route.productRoute(productService: ProductService) {

    route("/products") {

        get("/") {
            val offset = call.request.queryParameters["offset"]?.toLongOrNull() ?: 0
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 50

            when (val products = productService.getAll(offset, limit)) {
                emptyList<ProductModel>() -> call.badRequest("Something went wrong. Please verify request.")
                else -> call.ok(products)
            }
        }

        get("/{id}") {
            val productId = call.parameters["id"]
            when {
                productId.isNullOrBlank() -> call.badRequest("productId is incorrect")
                productId.toIntOrNull() == null -> call.badRequest("productId must be a number")
                else -> {
                    when (val product = productService.findProductById(productId.toInt())) {
                        null -> call.badRequest("Product with id:${productId} not found")
                        else -> call.ok(product)
                    }
                }
            }
        }

        authenticate("jwt-token") {

            post("/create") {
                val request = call.receive<ProductRequestModel>()

                when {
                    request.name.isNullOrEmpty() -> call.badRequest("name cannot be null or empty")
                    request.price <= 0 -> call.badRequest("price must be greater than 0")
                    request.description.isNullOrEmpty() -> call.badRequest("description cannot be null or empty")
                    else -> {
                        when (val product = productService.createProduct(request)) {
                            null -> call.badRequest("Something went wrong. Please verify request.")
                            else -> call.ok(product)
                        }
                    }
                }
            }

            put("/{id}") {
                val productId = call.parameters["id"]
                val request = call.receive<ProductUpdateModel>()

                when {
                    productId.isNullOrBlank() -> call.badRequest("productId is incorrect")
                    productId.toIntOrNull() == null -> call.badRequest("productId must be a number")
                    else -> {
                        when (val product = productService.updateProductById(productId.toInt(), request)) {
                            null -> call.badRequest("Something went wrong. Please verify request.")
                            else -> call.ok(product)
                        }
                    }
                }
            }

            delete("/{id}") {
                val productId = call.parameters["id"]
                when {
                    productId.isNullOrBlank() -> call.badRequest("productId is incorrect")
                    productId.toIntOrNull() == null -> call.badRequest("productId must be a number")
                    else -> {
                        when (val response = productService.deleteProductById(productId.toInt())) {
                            null -> call.badRequest("Something went wrong. Please verify request.")
                            else -> call.ok(response)
                        }
                    }
                }
            }
        }
    }
}