package services.internal

import utils.ConfigHelper.Companion.appConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import database.OrderEntity
import database.ProductEntity
import database.UserEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseHelper {

    private val database = hikariDataSource()

    fun connectDatabase(): DatabaseHelper {
        Database.connect(database)
        return this
    }

    fun createDatabase(): DatabaseHelper {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                tables = arrayOf(
                    UserEntity,
                    OrderEntity,
                    ProductEntity,
                )
            )
            SchemaUtils.checkCycle()
        }
        return this
    }

    private fun hikariDataSource(): HikariDataSource {
        val config = HikariConfig().apply {
            jdbcUrl = System.getenv("DATABASE_URL") ?: appConfig.database.jdbc
            schema = appConfig.database.schema
            driverClassName = appConfig.database.driver
            username = appConfig.database.username
            password = appConfig.database.password
        }

        return HikariDataSource(config)
    }

//    private fun createAdminUser() {
//        UserEntity.insert {
//            it[UserName] = "random"
//            ...
//    }
}