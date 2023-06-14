package rs.peles.domain.model.request

/**
 * Request for user register
 */
class RegisterUserRequest(
    val photoBase64: String?,
    val name: String?,
    val email: String,
    val password: String,
    val website: String?
)