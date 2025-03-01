package plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import models.utils.ErrorResponseModel
import utils.ConfigHelper.Companion.appConfig
import utils.unauthorized
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.date.*
import kotlinx.serialization.Serializable

fun Application.configureSecurity() {

    authentication {
        jwt("jwt-token") {
            realm = appConfig.jwt.realm
            verifier(
                JWT.require(Algorithm.HMAC512(appConfig.jwt.secret))
                    .withAudience(appConfig.jwt.audience)
                    .withSubject(appConfig.jwt.subject)
                    .withIssuer(appConfig.jwt.domain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(appConfig.jwt.audience)
                    && credential.payload.expiresAt.time > getTimeMillis()
                ) {
                    JWTPrincipal(credential.payload)
                } else null
            }
            // if the token is invalid or not set, this will be triggered.
            challenge { _, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = ErrorResponseModel(
                        code = HttpStatusCode.Unauthorized.value,
                        reason = "The token is invalid."
                    )
                )
            }
        }
    }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
}

@Serializable
data class MySession(val count: Int = 0)