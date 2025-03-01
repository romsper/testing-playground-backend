package utils

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*


class ConfigHelper {

    companion object {
        private val config = HoconApplicationConfig(ConfigFactory.load())

        val appConfig = AppConfigModel(
            ktor = KtorConfig(
                deployment = DeploymentConfig(
                    host = config.tryGetString("ktor.deployment.host") ?: "localhost",
                    port = config.tryGetString("ktor.deployment.port")?.toInt() ?: 1111,
                ),
                application = ApplicationConfig(
                    modules = config.tryGetStringList("ktor.application.modules") ?: emptyList(),
                ),
            ),
            database = DbConfig(
                username = config.tryGetString("database.username") ?: "",
                password = config.tryGetString("database.password") ?: "",
                jdbc = config.tryGetString("database.jdbc") ?: "",
                schema = config.tryGetString("database.schema") ?: "public",
                driver = config.tryGetString("database.driver") ?: "",
            ),
            jwt = JwtConfig(
                secret = config.tryGetString("jwt.secret") ?: "",
                domain = config.tryGetString("jwt.domain") ?: "",
                audience = config.tryGetString("jwt.audience") ?: "",
                realm = config.tryGetString("jwt.realm") ?: "",
                subject = config.tryGetString("jwt.subject") ?: "",
                accessExpireInDays = config.tryGetString("jwt.accessExpireInDays")?.toInt() ?: 1,
                refreshExpireInDays = config.tryGetString("jwt.refreshExpireInDays")?.toInt() ?: 7,
            ),
            email = EmailConfig(
                username = config.tryGetString("email.username") ?: "",
                password = config.tryGetString("email.password") ?: "",
                smtp = config.tryGetString("email.smtp") ?: "",
                port = config.tryGetString("email.port")?.toInt() ?: 587,
            ),
        )
    }
}

data class AppConfigModel(
    val ktor: KtorConfig,
    val database: DbConfig,
    val jwt: JwtConfig,
    val email: EmailConfig,
)

data class KtorConfig(
    val deployment: DeploymentConfig,
    val application: ApplicationConfig,
)

data class DeploymentConfig(
    val host: String,
    val port: Int,
)

data class ApplicationConfig(
    val modules: List<String>,
)

data class DbConfig(
    val password: String,
    val username: String,
    val jdbc: String,
    val schema: String,
    val driver: String
)

data class JwtConfig(
    val secret: String,
    val domain: String,
    val audience: String,
    val realm: String,
    val subject: String,
    val accessExpireInDays: Int,
    val refreshExpireInDays: Int,
)

data class EmailConfig(
    val username: String,
    val password: String,
    val smtp: String,
    val port: Int,
)