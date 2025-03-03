package repositories


import database.UserEntity
import database.toModel
import database.toResponseModel
import models.user.UserModel
import models.user.UserRequestModel
import models.user.UserResponseModel
import models.user.UserUpdateModel
import models.utils.SuccessResponseModel
import org.jetbrains.exposed.sql.deleteReturning
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning
import org.joda.time.DateTime
import utils.ChCrypto
import utils.executeQuery
import java.util.*
import kotlin.collections.firstOrNull
import kotlin.collections.map
import kotlin.let

class UserRepository {

    suspend fun getAllUsers(offset: Long, limit: Int): List<UserModel> {
        return executeQuery {
            UserEntity.selectAll()
                .limit(limit)
                .offset(offset)
                .map { UserEntity.toModel(it) }
        } ?: emptyList()
    }

    suspend fun findUserByEmail(email: String): UserModel? {
        return executeQuery {
            UserEntity.selectAll().where { UserEntity.Email.eq(email) }.firstOrNull()
                ?.let { UserEntity.toModel(it) }
        }
    }

    suspend fun findUserById(id: Int): UserModel? {
        return executeQuery {
            UserEntity.selectAll().where { UserEntity.id.eq(id) }.firstOrNull()
                ?.let { UserEntity.toModel(it) }
        }
    }

    suspend fun createUser(user: UserRequestModel): UserModel? {
        return executeQuery {
            UserEntity.insertReturning {
                it[UserName] = user.username ?: ""
                it[Password] = ChCrypto.aesEncrypt(user.password ?: "")
                it[Email] = user.email ?: ""
                it[CreatedAt] = DateTime.now().millis
            }.map { UserEntity.toModel(it) }.firstOrNull()
        }
    }

    suspend fun updateUser(userId: Int, user: UserUpdateModel): UserModel? {
        return executeQuery {
            UserEntity.updateReturning(where = { UserEntity.id eq userId }) {
                user.username?.let { username -> it[UserName] = username }
                user.password?.let { password -> it[Password] = ChCrypto.aesEncrypt(password) }
                user.phoneNumber?.let { phoneNumber -> it[PhoneNumber] = phoneNumber }
                user.email?.let { email -> it[Email] = email }
            }.map { UserEntity.toModel(it) }.firstOrNull()
        }
    }

    suspend fun deleteUserById(id: Int): SuccessResponseModel? {
        return executeQuery {
            UserEntity.deleteReturning { UserEntity.id.eq(id) }
                .map {
                    if (it.hashCode() == 0) null
                    else SuccessResponseModel(message = "User with userId:$id deleted successfully.")
                }.firstOrNull()
        }
    }
}