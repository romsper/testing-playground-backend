package services.internal

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import models.auth.AuthResponseModel
import models.user.UserModel
import org.joda.time.DateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import utils.ConfigHelper.Companion.appConfig
import java.util.UUID

class JWTProvider {
    private val log: Logger = LoggerFactory.getLogger(JWTProvider::class.java)

    fun generateToken(user: UserModel): AuthResponseModel {
        val createdAt = DateTime.now().millis
        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)
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
        log.info("Verifying access token")
        return try {
            JWT.require(Algorithm.HMAC512(appConfig.jwt.secret))
                .withAudience(appConfig.jwt.audience)
                .withIssuer(appConfig.jwt.domain)
                .withSubject(appConfig.jwt.subject)
                .withClaim("type", "access")
                .build()
                .verify(token)
        } catch (ex: JWTVerificationException) {
            log.warn("Access token verification failed", ex)
            null
        }
    }

    fun verifyRefreshToken(refreshToken: String): DecodedJWT? {
        log.info("Verifying refresh token")
        return try {
            JWT.require(Algorithm.HMAC512(appConfig.jwt.secret))
                .withAudience(appConfig.jwt.audience)
                .withIssuer(appConfig.jwt.domain)
                .withSubject(appConfig.jwt.subject)
                .withClaim("type", "refresh")
                .build()
                .verify(refreshToken)
        } catch (ex: JWTVerificationException) {
            log.warn("Refresh token verification failed", ex)
            null
        }
    }

    private fun createAccessToken(user: UserModel): String {
        return JWT.create()
            .withAudience(appConfig.jwt.audience)
            .withIssuer(appConfig.jwt.domain)
            .withSubject(appConfig.jwt.subject)
            .withExpiresAt(DateTime.now().plusDays(appConfig.jwt.accessExpireInDays).toDate())
            .withClaim("type", "access")
            .withClaim("email", user.email)
            .withClaim("name", user.username)
            .withClaim("jti", UUID.randomUUID().toString())
            .sign(Algorithm.HMAC512(appConfig.jwt.secret))
    }

    private fun createRefreshToken(user: UserModel): String {
        return JWT.create()
            .withAudience(appConfig.jwt.audience)
            .withIssuer(appConfig.jwt.domain)
            .withSubject(appConfig.jwt.subject)
            .withExpiresAt(DateTime.now().plusDays(appConfig.jwt.refreshExpireInDays).toDate())
            .withClaim("type", "refresh")
            .withClaim("email", user.email)
            .withClaim("name", user.username)
            .withClaim("jti", UUID.randomUUID().toString())
            .sign(Algorithm.HMAC512(appConfig.jwt.secret))
    }
}