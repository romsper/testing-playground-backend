package services

import models.user.UserModel
import models.user.UserRequestModel
import models.user.UserResponseModel
import repositories.UserRepository

class UserService(val userRepository: UserRepository) {

    suspend fun getAllUsers(offset: Long, limit: Int): List<UserResponseModel>? {
        return userRepository.getAllUsers(offset, limit)
    }

    suspend fun createUser(user: UserRequestModel): UserResponseModel? {
        return userRepository.createUser(user)
    }

    suspend fun findUserByEmail(email: String): UserResponseModel? {
        return userRepository.findUserByEmail(email)
    }

    suspend fun findUserByEmailFullModel(email: String): UserModel? {
        return userRepository.findUserByEmailFullModel(email)
    }

    suspend fun findUserById(id: Int): UserResponseModel? {
        return userRepository.findUserById(id)
    }
}