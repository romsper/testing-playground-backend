import services.internal.JWTProvider
import services.internal.DatabaseHelper
import controllers.authRoute
import controllers.orderRoute
import controllers.productRoute
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
import repositories.OrderRepository
import repositories.ProductRepository
import repositories.UserRepository
import services.OrderService
import services.ProductService
import services.UserService


fun main(args: Array<String>) {
    ConfigHelper()

    embeddedServer(
        factory = Netty,
        port = appConfig.ktor.deployment.port, // This is the port on which Ktor is listening
        host = appConfig.ktor.deployment.host,
        module = Application::module,
    ).start(wait = true)
}

fun Application.module() {
    DatabaseHelper()
        .connectDatabase()
        .createDatabase()

    val jwtProvider = JWTProvider()
    val userService = UserService(UserRepository())
    val orderService = OrderService(OrderRepository(), ProductRepository(), UserRepository())
    val productService = ProductService(ProductRepository())

    configureStatusPage()
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureHTTP()

    routing {
        route("api/v1") {
            authRoute(jwtProvider, userService)
            userRoute(userService)
            orderRoute(orderService)
            productRoute(productService)
        }
    }
}
