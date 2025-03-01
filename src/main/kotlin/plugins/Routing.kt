package plugins

import io.ktor.server.application.*

fun Application.configureRouting() {

//    routing {
//        get("/") {
//            val param = context.parameters["key"]
//            call.respondText("Hello World! $param")
//        }
//
//        post("/signup") {
//            val response = call.receive(UserResponseModel::class)
//            call.respondText(response.toString(), ContentType.Application.Json)
//        }
//
//        authenticate {
//            get("/auth") {
//                call.request.parseAuthorizationHeader().toString()
//                val param = context.parameters["key"]
//                call.respondText("Hello World! ${param}")
//            }
//
//            post("/signin") {
//                val token = call.request.parseAuthorizationHeader().toString()
//                val response = call.receive(UserResponseModel::class)
//                call.respondText(response.toString(), ContentType.Application.Json)
//            }
//        }
//    }
}