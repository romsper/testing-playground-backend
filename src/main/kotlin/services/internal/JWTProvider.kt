package services.internal

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import models.auth.AuthResponseModel
import models.user.UserModel
import utils.ConfigHelper.Companion.appConfig
import org.joda.time.DateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JWTProvider {
    private val log: Logger = LoggerFactory.getLogger(JWTProvider::class.java)

    fun generateToken(user: UserModel): AuthResponseModel {
        val createdAt = DateTime.now().millis
        val accessToken = createToken(user, appConfig.jwt.accessExpireInDays)
        val refreshToken = createToken(user, appConfig.jwt.refreshExpireInDays)
        log.info("Generated tokens for user: ${user.email}")

        return AuthResponseModel(
            id = user.id,
            accessToken = accessToken,
            refreshToken = refreshToken,
            createdAt = createdAt,
            expireInMs = DateTime(DateTime.now()).plusDays(appConfig.jwt.accessExpireInDays).toDate().time
        )
    }

    fun verifyToken(token: String): DecodedJWT? {
        log.info("Verifying token")
        return JWT.require(Algorithm.HMAC512(appConfig.jwt.secret)).build().verify(token)
    }

    private fun createToken(user: UserModel, expireInDays: Int): String {
        log.info("Creating token for user: ${user.email}")
        return JWT.create()
            .withAudience(appConfig.jwt.audience)
            .withIssuer(appConfig.jwt.domain)
            .withExpiresAt(DateTime(DateTime.now()).plusDays(expireInDays).toDate())
            .withSubject(appConfig.jwt.subject)
            .withClaim("email", user.email)
            .withClaim("name", user.username)
            .sign(Algorithm.HMAC512(appConfig.jwt.secret))
    }
}