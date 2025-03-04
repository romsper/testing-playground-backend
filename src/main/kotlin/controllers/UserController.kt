package controllers

import models.user.UserRequestModel
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import models.user.UserUpdateModel
import services.UserService
import utils.badRequest
import utils.notFound
import utils.ok

fun Route.userRoute(userService: UserService) {

    route("/users") {

        post("/create") {
            val request = call.receive<UserRequestModel>()

            when {
                request.email.isNullOrBlank() || request.password.isNullOrBlank() || request.username.isNullOrBlank() -> call.badRequest(
                    "User details cannot be null or blank"
                )

                else -> {
                    when (val user = userService.createUser(request)) {
                        null -> call.badRequest("Something went wrong. Please verify request.")
                        else -> call.ok(user.toShortModel())
                    }
                }
            }
        }

        authenticate("jwt-token") {

            get("/") {
                val offset = call.request.queryParameters["offset"]?.toLongOrNull() ?: 0
                val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 10

                when (val users = userService.getAllUsers(offset, limit)) {
                    null -> call.badRequest("Something went wrong. Please verify request.")
                    else -> call.ok(users)
                }
            }

            get("/{id}") {
                val userId = call.parameters["id"]
                when {
                    userId.isNullOrBlank() -> call.badRequest("userId is incorrect")
                    userId.toIntOrNull() == null -> call.badRequest("userId must be a number")
                    else -> {
                        when (val user = userService.findUserById(userId.toInt())) {
                            null -> call.notFound("User with id:${userId} not found")
                            else -> call.ok(user.toShortModel())
                        }
                    }
                }
            }

            get("/me") {
                val principal = call.principal<JWTPrincipal>()
                when {
                    principal == null -> call.badRequest("Invalid principal")
                    else -> {
                        val email = principal.payload.getClaim("email").asString()
                        when (val appUser = userService.findUserByEmail(email)) {
                            null -> call.notFound("User with email:$email not found")
                            else -> call.ok(appUser.toShortModel())
                        }
                    }
                }
            }

            put("/{id}") {
                val userId = call.parameters["id"]
                val request = call.receive<UserUpdateModel>()

                when {
                    userId.isNullOrBlank() -> call.badRequest("userId is incorrect")
                    userId.toIntOrNull() == null -> call.badRequest("userId must be a number")
                    else -> {
                        when (val user = userService.updateUser(userId.toInt(), request)) {
                            null -> call.badRequest("Something went wrong. Please verify request.")
                            else -> call.ok(user.toShortModel())
                        }
                    }
                }
            }

            delete("/{id}") {
                val userId = call.parameters["id"]
                when {
                    userId.isNullOrBlank() -> call.badRequest("userId is incorrect")
                    userId.toIntOrNull() == null -> call.badRequest("userId must be a number")
                    else -> {
                        when (val response = userService.deleteUserById(userId.toInt())) {
                            null -> call.badRequest("Something went wrong. Please verify request.")
                            else -> call.ok(response)
                        }
                    }
                }
            }
        }
    }
}