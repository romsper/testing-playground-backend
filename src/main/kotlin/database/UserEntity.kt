package database

import models.user.UserModel
import models.user.UserResponseModel
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import kotlin.toString

object UserEntity : IntIdTable("table_users") {
    var UserName = varchar("username", 60)
    var Password = varchar("password", 100)
    var Email = varchar("email", 100).uniqueIndex()
    var PhoneNumber = varchar("phoneNumber", 50).nullable()
    var CreatedAt = long("createdAt")
}

fun UserEntity.toModel(resultRow: ResultRow) = UserModel(
    id = resultRow[id].value,
    username = resultRow[UserName],
    password = resultRow[Password],
    email = resultRow[Email],
    phoneNumber = resultRow[PhoneNumber] ?: "",
    createdAt = resultRow[CreatedAt]
)

fun UserEntity.toResponseModel(resultRow: ResultRow) = UserResponseModel(
    id = resultRow[id].value,
    username = resultRow[UserName],
    email = resultRow[Email],
    phoneNumber = resultRow[PhoneNumber],
    createdAt = resultRow[CreatedAt]
)
