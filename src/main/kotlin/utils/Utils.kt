package utils

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

inline fun <reified T : Enum<T>> enumContains(name: String): Boolean {
    return enumValues<T>().any { it.name.contains(name, true) }
}

suspend fun <T> executeQuery(transaction: () -> T): T? {
    return try {
        suspendedTransactionAsync(context = Dispatchers.IO) {
            transaction()
        }.await()
    } catch (e: Exception) {
        println("Exception caught in executeQuery: ${e.message}")
        null
    }
}
