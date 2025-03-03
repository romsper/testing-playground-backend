package database

import models.product.ProductModel
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

object ProductEntity : IntIdTable("table_products") {
    var Name = varchar("name", 60)
    var Description = varchar("description", 100)
    var Price = double("price")
}

fun ProductEntity.toModel(resultRow: ResultRow) = ProductModel(
    id = resultRow[id].value,
    name = resultRow[Name],
    description = resultRow[Description],
    price = resultRow[Price]
)