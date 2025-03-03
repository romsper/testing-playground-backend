package exceptions

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
class NotFoundException : RuntimeException()
class InternalErrorException : RuntimeException()
class BadRequestException : RuntimeException()
class IllegalArgumentException : RuntimeException()