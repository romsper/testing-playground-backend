package plugins

import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.exceptions.AuthenticationException
import com.exceptions.AuthorizationException
import com.exceptions.InternalErrorException
import com.exceptions.NotFoundException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import utils.badRequest
import utils.forbidden
import utils.internalServerError
import utils.notFound
import utils.unauthorized

fun Application.configureStatusPage() {

    install(StatusPages) {
        exception<SignatureVerificationException> { call, cause ->
            call.badRequest(reason = "The token is invalid")
        }

        exception<TokenExpiredException> { call, cause ->
            call.unauthorized(reason = "The token has expired")
        }

        exception<AuthenticationException> { call, cause ->
            call.unauthorized(reason = "${cause.message}")
        }

        exception<AuthorizationException> { call, cause ->
            call.forbidden(reason = "${cause.message}")
        }

        exception<NotFoundException> { call, cause ->
            call.notFound(reason = "${cause.message}")
        }

        exception<InternalErrorException> { call, cause ->
            call.internalServerError(reason = "${cause.message}")
        }

        exception<IllegalStateException> { call, cause ->
            call.internalServerError(reason = "${cause.message}")
        }
    }
}