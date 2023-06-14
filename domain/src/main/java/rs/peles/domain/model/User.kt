package rs.peles.domain.model

/**
 * Class that represents one User
 */
data class User(
    val name: String,
    val email: String,
    val website: String,
    val photoUrl: String
)