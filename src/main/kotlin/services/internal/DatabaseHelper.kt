package services.internal

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import database.OrderEntity
import database.ProductEntity
import database.UserEntity
import enums.OrderStatusEnum
import models.product.ProductModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import utils.ChCrypto
import utils.ConfigHelper.Companion.appConfig

class DatabaseHelper(val preset: Boolean = false) {

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
            if (preset) generatePresetData()
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

    private fun generatePresetData() {
        if (
            UserEntity.selectAll().count() > 0 ||
            ProductEntity.selectAll().count() > 0 ||
            OrderEntity.selectAll().count() > 0
        ) return

        UserEntity.insert {
                it[UserName] = "user"
                it[Password] = ChCrypto.aesEncrypt("password")
                it[Email] = "user@autotest.com"
                it[PhoneNumber] = "12345678"
                it[CreatedAt] = DateTime.now().millis
        }

        ProductEntity.insert {
            it[Name] = "Coca Cola"
            it[Description] = "Refreshing beverage"
            it[Price] = 2.33
        }

        ProductEntity.insert {
            it[Name] = "Coffee"
            it[Description] = "Hot beverage"
            it[Price] = 4.49
        }

        ProductEntity.insert {
            it[Name] = "Tea"
            it[Description] = "Soothing beverage"
            it[Price] = 3.25
        }

        ProductEntity.insert {
            it[Name] = "Bubble Tea"
            it[Description] = "Sweet and chewy"
            it[Price] = 2.00
        }

        ProductEntity.insert {
            it[Name] = "Water"
            it[Description] = "Pure and simple"
            it[Price] = 1.00
        }

        ProductEntity.insert {
            it[Name] = "Juice"
            it[Description] = "Fruity and delicious"
            it[Price] = 3.75
        }

        OrderEntity.insert {
            it[UserId] = 1
            it[OrderStatus] = OrderStatusEnum.PENDING
            it[Products] =
                listOf(
                    ProductModel(id = 1, name = "Coca Cola", description = "Refreshing beverage", price = 2.33),
                    ProductModel(id = 2, name = "Coffee", description = "Hot beverage", price = 4.49),
                    ProductModel(id = 2, name = "Coffee", description = "Hot beverage", price = 4.49)
                )
            it[TotalAmount] = 2.33 + 4.49 + 4.49
            it[UpdatedAt] = DateTime.now().millis
            it[CreatedAt] = DateTime.now().millis
        }

        OrderEntity.insert {
            it[UserId] = 1
            it[OrderStatus] = OrderStatusEnum.IN_PROGRESS
            it[Products] =
                listOf(
                    ProductModel(id = 5, name = "Water", description = "Pure and simple", price = 1.00)
                )
            it[TotalAmount] = 1.00
            it[UpdatedAt] = DateTime.now().millis
            it[CreatedAt] = DateTime.now().millis
        }

        OrderEntity.insert {
            it[OrderStatus] = OrderStatusEnum.COMPLETED
            it[Products] =
                listOf(
                    ProductModel(id = 3, name = "Tea", description = "Soothing beverage", price = 3.25),
                    ProductModel(id = 4, name = "Bubble Tea", description = "Sweet and chewy", price = 2.00)
                )
            it[TotalAmount] = 3.25 + 2.00
            it[UpdatedAt] = DateTime.now().millis
            it[CreatedAt] = DateTime.now().millis
        }
    }
}