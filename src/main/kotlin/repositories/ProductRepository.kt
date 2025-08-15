package repositories

import database.ProductEntity
import database.toModel
import models.product.ProductModel
import models.product.ProductRequestModel
import models.product.ProductUpdateModel
import models.utils.SuccessResponseModel
import org.jetbrains.exposed.sql.deleteReturning
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning
import utils.executeQuery

class ProductRepository {

    suspend fun getAllProducts(offset: Long, limit: Int): List<ProductModel> {
        return executeQuery {
            ProductEntity.selectAll()
                .limit(limit.coerceAtMost(50))
                .offset(offset)
                .map { ProductEntity.toModel(it) }
        } ?: emptyList()
    }

    suspend fun findProductById(id: Int): ProductModel? {
        return executeQuery {
            ProductEntity.selectAll().where { ProductEntity.id.eq(id) }.firstOrNull()
                ?.let { ProductEntity.toModel(it) }
        }
    }

    suspend fun createProduct(product: ProductRequestModel): ProductModel? {
        return executeQuery {
            ProductEntity.insertReturning {
                it[Name] = product.name
                it[Description] = product.description
                it[Price] = product.price
            }.firstOrNull()?.let { ProductEntity.toModel(it) }
        }
    }

    suspend fun updateProductById(id: Int, product: ProductUpdateModel): ProductModel? {
        return executeQuery {
            ProductEntity.updateReturning(where = { ProductEntity.id.eq(id) }) {
                product.name?.let { name -> it[Name] = name }
                product.description?.let { description -> it[Description] = description }
                product.price?.let { price -> it[Price] = price }
            }.firstOrNull()?.let { ProductEntity.toModel(it) }
        }
    }

    suspend fun deleteProductById(id: Int): SuccessResponseModel? {
        return executeQuery {
            ProductEntity.deleteReturning { ProductEntity.id.eq(id) }
                .map {
                    if (it.hashCode() == 0) null
                    else SuccessResponseModel(message = "Product with productId:$id deleted successfully")
                }.firstOrNull()
        }
    }
}