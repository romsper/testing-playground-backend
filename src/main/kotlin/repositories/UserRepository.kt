package repositories


import database.UserEntity
import database.toFullModel
import database.toModel
import models.user.UserActivateModel
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

    suspend fun getAllUsers(offset: Long, limit: Int): List<UserResponseModel>? {
        return executeQuery {
            UserEntity.selectAll()
                .limit(limit)
                .offset(offset)
                .map { UserEntity.toModel(it) }
        }
    }

    suspend fun findUserByUUID(uuid: UUID): UserResponseModel? {
        return executeQuery {
            UserEntity.selectAll().where { UserEntity.ActivationUUID.eq(uuid) }.firstOrNull()
                ?.let { UserEntity.toModel(it) }
        }
    }

    suspend fun findUserByEmail(email: String): UserResponseModel? {
        return executeQuery {
            UserEntity.selectAll().where { UserEntity.Email.eq(email) }.firstOrNull()
                ?.let { UserEntity.toModel(it) }
        }
    }

    suspend fun findUserByEmailFullModel(email: String): UserModel? {
        return executeQuery {
            UserEntity.selectAll().where { UserEntity.Email.eq(email) }.firstOrNull()
                ?.let { UserEntity.toFullModel(it) }
        }
    }

    suspend fun findUserById(id: Int): UserResponseModel? {
        return executeQuery {
            UserEntity.selectAll().where { UserEntity.id.eq(id) }.firstOrNull()
                ?.let { UserEntity.toModel(it) }
        }
    }

    suspend fun createUser(user: UserRequestModel): UserResponseModel? {
        return executeQuery {
            UserEntity.insertReturning {
                it[UserName] = user.username ?: ""
                it[Password] = ChCrypto.aesEncrypt(user.password ?: "")
                it[Email] = user.email ?: ""
                it[ActivationUUID] = UUID.randomUUID()
                it[CreatedAt] = DateTime.now().millis
            }.map { UserEntity.toModel(it) }.firstOrNull()
        }
    }

    suspend fun activateUser(uuid: UUID, user: UserActivateModel, encryptedPassword: String): UserResponseModel? {
        return executeQuery {
            UserEntity.updateReturning(where = { UserEntity.ActivationUUID eq uuid }) {
                it[UserName] = user.username
                it[Password] = encryptedPassword
                it[Activated] = true
                it[ActivationUUID] = null
                user.phoneNumber?.let { phoneNumber -> it[PhoneNumber] = phoneNumber }
            }.map { UserEntity.toModel(it) }.firstOrNull()
        }
    }

    suspend fun updateUser(userId: Int, user: UserUpdateModel, encryptedPassword: String?): UserResponseModel? {
        return executeQuery {
            UserEntity.updateReturning(where = { UserEntity.id eq userId }) {
                user.username?.let { username -> it[UserName] = username }
                encryptedPassword?.let { password -> it[Password] = password }
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