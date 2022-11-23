package rs.peles.data.source

import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetLocalUserRequest
import rs.peles.domain.model.request.GetUserRequest

/**
 * General Data Source
 */
interface UserDataSource {

    /**
     * Remote Data Source
     */
    interface Remote {
        suspend fun getUsers(): List<User>
        suspend fun getSpecificUser(userRequest: GetUserRequest): User
    }

    /**
     * Local Data Source
     */
    interface Local {
        suspend fun getSpecificUser(userRequest: GetLocalUserRequest): User
    }

    /**
     * Cache Data Source
     */
    interface Cache : Local

}