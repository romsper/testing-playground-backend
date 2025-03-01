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
    var ActivationUUID = uuid("activationUUID").nullable()
    var Activated = bool("activated").default(false)
    var CreatedAt = long("createdAt")
}

fun UserEntity.toModel(resultRow: ResultRow) = UserResponseModel(
    id = resultRow[id].value,
    username = resultRow[UserName],
    email = resultRow[Email],
    resultRow[ActivationUUID].toString(),
    resultRow[PhoneNumber],
    resultRow[Activated],
)

fun UserEntity.toFullModel(resultRow: ResultRow) = UserModel(
    resultRow[id].value,
    resultRow[UserName],
    resultRow[Password],
    resultRow[Email],
    resultRow[PhoneNumber] ?: "",
    resultRow[Activated],
    resultRow[CreatedAt]
)