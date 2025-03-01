import services.internal.JWTProvider
import services.internal.DatabaseHelper
import controllers.authRoute
import controllers.userRoute
import utils.ConfigHelper
import utils.ConfigHelper.Companion.appConfig
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import plugins.configureHTTP
import plugins.configureMonitoring
import plugins.configureSecurity
import plugins.configureSerialization
import plugins.configureStatusPage
import repositories.UserRepository
import services.UserService


fun main(args: Array<String>) {
    ConfigHelper()

    DatabaseHelper()
        .connectDatabase()
        .createDatabase()

    embeddedServer(
        factory = Netty,
        port = appConfig.ktor.deployment.port, // This is the port on which Ktor is listening
        host = appConfig.ktor.deployment.host,
        module = Application::module,
    ).start(wait = true)
}

fun Application.module() {
    val jwtProvider = JWTProvider()
    val userService = UserService(UserRepository())

    configureStatusPage()
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureHTTP()

    routing {
        route("api/v1") {
            userRoute(userService)
            authRoute(jwtProvider, userService)
        }
    }
}
