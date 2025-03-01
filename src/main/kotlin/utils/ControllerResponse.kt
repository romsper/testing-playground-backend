package utils

import models.utils.ErrorResponseModel
import models.utils.SuccessResponseModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.ok(model: Any) = this.respond(status = HttpStatusCode.OK, message = model)

suspend fun ApplicationCall.success(message: String) = this.respond(
    status = HttpStatusCode.OK, message = SuccessResponseModel(
        code = HttpStatusCode.OK.value,
        message = message
    )
)

suspend fun ApplicationCall.unauthorized(reason: String) = this.respond(
    status = HttpStatusCode.Unauthorized, message = ErrorResponseModel(
        code = HttpStatusCode.Unauthorized.value,
        reason = reason
    )
)

suspend fun ApplicationCall.notFound(reason: String) = this.respond(
    status = HttpStatusCode.NotFound, message = ErrorResponseModel(
        code = HttpStatusCode.NotFound.value,
        reason = reason
    )
)

suspend fun ApplicationCall.badRequest(reason: String) = this.respond(
    status = HttpStatusCode.BadRequest, message = ErrorResponseModel(
        code = HttpStatusCode.BadRequest.value,
        reason = reason
    )
)

suspend fun ApplicationCall.forbidden(reason: String) = this.respond(
    status = HttpStatusCode.Forbidden, message = ErrorResponseModel(
        code = HttpStatusCode.Forbidden.value,
        reason = reason
    )
)

suspend fun ApplicationCall.internalServerError(reason: String) = this.respond(
    status = HttpStatusCode.InternalServerError, message = ErrorResponseModel(
        code = HttpStatusCode.InternalServerError.value,
        reason = reason
    )
)
