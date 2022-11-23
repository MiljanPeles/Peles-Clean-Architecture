package rs.peles.data.repository

import rs.peles.domain.model.User
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
        // Local business
    }

    /**
     * Cache Data Source
     */
    interface Cache : Local

}