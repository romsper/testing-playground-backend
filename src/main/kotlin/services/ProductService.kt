package services

import models.product.ProductRequestModel
import models.product.ProductUpdateModel
import repositories.ProductRepository

class ProductService(private val productRepository: ProductRepository) {

    suspend fun getAll(offset: Long, limit: Int) = productRepository.getAllProducts(offset, limit)

    suspend fun findProductById(id: Int) = productRepository.findProductById(id)

    suspend fun createProduct(product: ProductRequestModel) = productRepository.createProduct(product)

    suspend fun updateProductById(id: Int, product: ProductUpdateModel) = productRepository.updateProductById(id, product)

    suspend fun deleteProductById(id: Int) = productRepository.deleteProductById(id)
}