package services

import models.user.UserRequestModel
import models.user.UserUpdateModel
import repositories.UserRepository

class UserService(private val userRepository: UserRepository) {

    suspend fun getAllUsers(offset: Long, limit: Int) = userRepository.getAllUsers(offset, limit)

    suspend fun createUser(user: UserRequestModel )= userRepository.createUser(user)

    suspend fun findUserByEmail(email: String) = userRepository.findUserByEmail(email)

    suspend fun findUserById(id: Int) = userRepository.findUserById(id)
    
    suspend fun updateUser(userId: Int, user: UserUpdateModel) = userRepository.updateUser(userId, user)
    
    suspend fun deleteUserById(id: Int) = userRepository.deleteUserById(id)
}