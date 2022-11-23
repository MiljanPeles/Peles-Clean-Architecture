package rs.peles.domain.model.request

/**
 * Request for specific local user
 */
class GetLocalUserRequest(
    val name: String,
    val lastname: String,
    val age: Int
)