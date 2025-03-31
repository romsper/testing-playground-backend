package controllers

import com.models.auth.AuthRefreshRequestModel
import com.models.auth.AuthRequestModel
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import services.UserService
import services.internal.JWTProvider
import utils.ChCrypto
import utils.badRequest
import utils.ok
import utils.unauthorized

fun Route.authRoute(jwtProvider: JWTProvider, userService: UserService) {

    route("/auth") {
        post("/login") {
            val request = call.receive<AuthRequestModel>()

            when {
                request.email.isNullOrBlank() || request.password.isNullOrBlank() -> call.badRequest("Email and Password cannot be null or blank")
                else -> when (val user = userService.findUserByEmail(request.email)) {
                    null -> call.badRequest("User with email:${request.email} not found")
                    else -> {
                        val decryptedPassword = ChCrypto.aesDecrypt(user.password)
                        when (decryptedPassword != request.password) {
                            true -> call.badRequest("Wrong password: $decryptedPassword | ${request.password}")
                            false -> call.ok(jwtProvider.generateToken(user))
                        }
                    }
                }
            }
        }

        post("/refresh") {
            val request = call.receive<AuthRefreshRequestModel>()

            when {
                request.refreshToken.isBlank() -> call.badRequest("Refresh token cannot be null or blank")
                else -> {
                    when (val principal = jwtProvider.verifyToken(request.refreshToken)) {
                        null -> call.badRequest("Refresh token is invalid")
                        else -> {
                            when (val user =
                                userService.findUserByEmail(principal.getClaim("email").asString())) {
                                null -> call.badRequest("User not found")
                                else -> call.ok(jwtProvider.generateToken(user))
                            }
                        }
                    }
                }
            }
        }

        authenticate("jwt-token") {
            get("/validate") {
                val principal = call.principal<JWTPrincipal>()
                when {
                    principal == null -> call.unauthorized("Unauthorized")
                    else -> {
                        val email = principal.payload.getClaim("email").asString()
                        val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                        when {
                            email.isNullOrBlank() -> call.badRequest("Email cannot be null or blank")
                            expiresAt == null || expiresAt <= 0 -> call.badRequest("Token is expired")
                            else -> call.ok("Hello, $email! Token is expired at $expiresAt expiresAt ms.")
                        }
                    }
                }
            }
        }
    }
}
